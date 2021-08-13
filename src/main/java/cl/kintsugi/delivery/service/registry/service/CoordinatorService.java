package cl.kintsugi.delivery.service.registry.service;

import cl.kintsugi.delivery.service.registry.models.entity.Coordinator;
import cl.kintsugi.delivery.service.registry.models.entity.commons.Connections;
import cl.kintsugi.delivery.service.registry.repository.ICoordinatorRepository;
import cl.kintsugi.delivery.service.registry.request.CoordinatorRequest;
import cl.kintsugi.delivery.service.registry.response.CoordinatorResponse;
import cl.kintsugi.delivery.service.registry.response.Response;
import cl.kintsugi.delivery.service.registry.service.utils.Formatter;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class CoordinatorService implements  ICoordinatorService{
    Logger logger = Logger.getLogger(CoordinatorService.class.getName());
    @Autowired
    private ICoordinatorRepository coordinatorRespository;
    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private Formatter formatter;
    @Autowired
    private Response response;

    public List<CoordinatorResponse> getAllCoordinators(){
        try {
            SearchRequest searchRequest = new SearchRequest("tibco-coordinators");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.size(1000);
            String[] includeFields = new String[] {"uuid","name","engineVersion","engineName","connections","engineType","url","deleted","updateDate"};
            searchRequest.source(searchSourceBuilder.fetchSource(includeFields, null));
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHit[] searchHits = searchResponse.getHits().getHits();
            List<CoordinatorResponse> coordinators = new ArrayList<>();
            for (SearchHit hit : searchHits) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                CoordinatorResponse coordinator = new CoordinatorResponse();
                coordinator.setUuid((String) sourceAsMap.get("uuid"));
                coordinator.setName((String) sourceAsMap.get("name"));
                coordinator.setEngineName((String) sourceAsMap.get("engineName"));
                coordinator.setEngineType((String) sourceAsMap.get("engineType"));
                coordinator.setEngineVersion((String) sourceAsMap.get("engineVersion"));
                coordinator.setUrl((String) sourceAsMap.get("url"));
                coordinator.setConnections((List<Connections>) sourceAsMap.get("connections"));
                coordinator.setDeleted((Boolean) sourceAsMap.get("deleted"));
                coordinator.setUpdateDate((String) sourceAsMap.get("updateDate"));
                coordinators.add(coordinator);
            }
            return coordinators;
        }catch(Exception e){
            logger.info(e.getMessage());
            return null;
        }
    }

    public Coordinator findCoordinatorByUuid(String uuid) {
        return coordinatorRespository.findCoordinatorByUuid(uuid);
    }

    public Coordinator saveCoordinator(CoordinatorRequest coordinatorRequest) {
        Coordinator coordinator = new Coordinator();
        UUID uuid = UUID.randomUUID();
        try{
            coordinator.setUuid("coord-" + uuid);
            coordinator.setTechnology("Tibco Businesswork");
            coordinator.setName(coordinatorRequest.getName());
            coordinator.setEngineName(coordinatorRequest.getEngineName());
            coordinator.setEngineVersion(coordinatorRequest.getEngineVersion());
            coordinator.setEngineType(coordinatorRequest.getEngineType());
            coordinator.setConnections(coordinatorRequest.getConnections());
            coordinator.setServers(coordinatorRequest.getServers());
            coordinator.setVip(coordinatorRequest.getVip());
            coordinator.setUrl(coordinatorRequest.getUrl());
            coordinator.setRequest(coordinatorRequest.getRequest());
            coordinator.setResponse(coordinatorRequest.getResponse());
            coordinator.setDeployedIn(coordinatorRequest.getDeployedIn());
            coordinator.setAvailability(coordinatorRequest.getAvailability());
            coordinator.setCreateDate(formatter.getTimeStamp());
            coordinator.setUpdateDate(formatter.getTimeStamp());
            coordinator.setUpdatedBy(coordinatorRequest.getUpdatedBy());
            coordinator.setDeleteDate(null);
            coordinator.setDeleted(false);
            coordinator.setAdditionalInfo(coordinatorRequest.getAdditionalInfo());

            Coordinator coordinatorNew = coordinatorRespository.save(coordinator);
            logger.info(coordinatorNew.toString());
            return  coordinatorNew;
        }catch (Exception e){
            logger.info(e.getMessage());
            return null;
        }
    }

    public Coordinator updateCoordinator(String uuid, CoordinatorRequest coordinatorRequest){
        logger.info("UUID: " + uuid);
        Coordinator coordinator = coordinatorRespository.findCoordinatorByUuid(uuid);
        logger.info(coordinator.toString());
        try{
            coordinator.setName(coordinatorRequest.getName());
            coordinator.setEngineName(coordinatorRequest.getEngineName());
            coordinator.setEngineVersion(coordinatorRequest.getEngineVersion());
            coordinator.setEngineType(coordinatorRequest.getEngineType());
            coordinator.setConnections(coordinatorRequest.getConnections());
            coordinator.setServers(coordinatorRequest.getServers());
            coordinator.setVip(coordinatorRequest.getVip());
            coordinator.setUrl(coordinatorRequest.getUrl());
            coordinator.setRequest(coordinatorRequest.getRequest());
            coordinator.setResponse(coordinatorRequest.getResponse());
            coordinator.setDeployedIn(coordinatorRequest.getDeployedIn());
            coordinator.setAvailability(coordinatorRequest.getAvailability());
            coordinator.setUpdateDate(formatter.getTimeStamp());
            coordinator.setUpdatedBy(coordinatorRequest.getUpdatedBy());
            if(coordinatorRequest.isDeleted() && !coordinator.isDeleted()){
                coordinator.setDeleteDate(formatter.getTimeStamp());
            }
            if(!coordinatorRequest.isDeleted() && coordinator.isDeleted()){
                coordinator.setDeleteDate(null);
            }
            coordinator.setDeleted(coordinatorRequest.isDeleted());
            coordinator.setAdditionalInfo(coordinatorRequest.getAdditionalInfo());
            logger.info(coordinator.toString());
            return coordinatorRespository.save(coordinator);
        }
        catch(Exception e){
            logger.info(e.getMessage());
            return null;
        }
    }

    public Response disableCoordinator(String uuid, String userName){
        logger.info("Eliminando documento de UUID: " + uuid + " por: " + userName);
        if(userName == null){
            userName = "default";
        }
        UpdateRequest request = new UpdateRequest("tibco-coordinators", uuid)
                .doc("deleteDate",formatter.getTimeStamp() ,
                        "updatedBy",userName,
                        "deleted", true);

        try{
            UpdateResponse updateResponse = client.update(request, RequestOptions.DEFAULT);
            response.setStatus(200);
            response.setMessage("disabled");
            return response;
        }
        catch(ElasticsearchException e){
            if (e.status() == RestStatus.CONFLICT) {
                System.out.println("Error");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Response deleteCoordinatorByUuid(String uuid){
        try{
            coordinatorRespository.deleteById(uuid);
            response.setStatus(200);
            response.setMessage("deleted");
            return response;
        }catch (Exception e){}
        return null;
    }
}
