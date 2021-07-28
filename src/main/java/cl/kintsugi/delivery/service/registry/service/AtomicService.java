package cl.kintsugi.delivery.service.registry.service;

import cl.kintsugi.delivery.service.registry.models.entity.Atomic;
import cl.kintsugi.delivery.service.registry.models.entity.commons.Connections;
import cl.kintsugi.delivery.service.registry.repository.IAtomicRepository;
import cl.kintsugi.delivery.service.registry.request.AtomicRequest;
import cl.kintsugi.delivery.service.registry.response.AtomicResponse;
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
public class AtomicService implements  IAtomicService{

    Logger logger = Logger.getLogger(AtomicService.class.getName());
    @Autowired
    private IAtomicRepository atomicRepository;
    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private Formatter formatter;
    @Autowired
    private Response response;

    @Override
    public List<AtomicResponse> getAllAtomics() {
        try {
            SearchRequest searchRequest = new SearchRequest("tibco-atomics");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.size(1000);
            String[] includeFields = new String[] {"uuid","name","version","engineName","connections","type","url","deleted","updatedBy"};
            searchRequest.source(searchSourceBuilder.fetchSource(includeFields, null));
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHit[] searchHits = searchResponse.getHits().getHits();
            List<AtomicResponse> atomics = new ArrayList<>();
            for (SearchHit hit : searchHits) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                AtomicResponse atomic = new AtomicResponse();
                atomic.setUuid((String) sourceAsMap.get("uuid"));
                atomic.setName((String) sourceAsMap.get("name"));
                atomic.setEngineName((String) sourceAsMap.get("engineName"));
                atomic.setType((String) sourceAsMap.get("type"));
                atomic.setVersion((String) sourceAsMap.get("version"));
                atomic.setUrl((String) sourceAsMap.get("url"));
                atomic.setConnections((List<Connections>) sourceAsMap.get("connections"));
                atomic.setDeleted((Boolean) sourceAsMap.get("deleted"));
                atomic.setUpdateDate((String) sourceAsMap.get("updatedBy"));
                atomics.add(atomic);
            }
            return atomics;
        }catch(Exception e){
            logger.info(e.getMessage());
            return null;
        }
    }

    @Override
    public Atomic findAtomicByUuid(String uuid) {
        return atomicRepository.findAtomicByUuid(uuid);
    }

    public Atomic saveAtomic(AtomicRequest atomicRequest) {
        Atomic atomic = new Atomic();
        UUID uuid = UUID.randomUUID();
        try{
            atomic.setUuid("ato-" + uuid);
            atomic.setTechnology("Tibco Businesswork");
            atomic.setName(atomicRequest.getName());
            atomic.setEngineName(atomicRequest.getEngineName());
            atomic.setVersion(atomicRequest.getVersion());
            atomic.setType(atomicRequest.getType());
            atomic.setConnections(atomicRequest.getConnections());
            atomic.setServers(atomicRequest.getServers());
            atomic.setVip(atomicRequest.getVip());
            atomic.setUrl(atomicRequest.getUrl());
            atomic.setRequest(atomicRequest.getRequest());
            atomic.setResponse(atomicRequest.getResponse());
            atomic.setDeployedIn(atomicRequest.getDeployedIn());
            atomic.setAvailability(atomicRequest.getAvailability());
            atomic.setWsdlPath(atomicRequest.getWsdlPath());
            atomic.setBackend(atomicRequest.getBackend());
            atomic.setCreateDate(formatter.getTimeStamp());
            atomic.setUpdateDate(formatter.getTimeStamp());
            atomic.setUpdatedBy(atomicRequest.getUpdatedBy());
            atomic.setDeleteDate(null);
            atomic.setDeleted(false);
            atomic.setAdditionalInfo(atomicRequest.getAdditionalInfo());
            return  atomicRepository.save(atomic);
        }catch (Exception e){
            logger.info(e.getMessage());
            return null;
        }
    }

    public Atomic updateAtomic(String uuid, AtomicRequest atomicRequest){
        logger.info("UUID: " + uuid);
        Atomic atomic = atomicRepository.findAtomicByUuid(uuid);
        logger.info(atomic.toString());
        try{
            atomic.setName(atomicRequest.getName());
            atomic.setEngineName(atomicRequest.getEngineName());
            atomic.setVersion(atomicRequest.getVersion());
            atomic.setType(atomicRequest.getType());
            atomic.setConnections(atomicRequest.getConnections());
            atomic.setServers(atomicRequest.getServers());
            atomic.setVip(atomicRequest.getVip());
            atomic.setUrl(atomicRequest.getUrl());
            atomic.setRequest(atomicRequest.getRequest());
            atomic.setResponse(atomicRequest.getResponse());
            atomic.setDeployedIn(atomicRequest.getDeployedIn());
            atomic.setAvailability(atomicRequest.getAvailability());
            atomic.setWsdlPath(atomicRequest.getWsdlPath());
            atomic.setBackend(atomicRequest.getBackend());
            atomic.setUpdateDate(formatter.getTimeStamp());
            atomic.setUpdatedBy(atomicRequest.getUpdatedBy());
            if(atomicRequest.isDeleted() && !atomic.isDeleted()){
                atomic.setDeleteDate(formatter.getTimeStamp());
            }
            if(!atomicRequest.isDeleted() && atomic.isDeleted()){
                atomic.setDeleteDate(null);
            }
            atomic.setDeleted(atomicRequest.isDeleted());
            atomic.setAdditionalInfo(atomicRequest.getAdditionalInfo());
            logger.info(atomic.toString());
            return atomicRepository.save(atomic);
        }
        catch(Exception e){
            logger.info(e.getMessage());
            return null;
        }
    }

    public Response disableAtomic(String uuid, String userName){
        logger.info("Eliminando documento de UUID: " + uuid + " por: " + userName);
        if(userName == null){
            userName = "default";
        }
        UpdateRequest request = new UpdateRequest("tibco-atomics", uuid)
                .doc("deleteDate",formatter.getTimeStamp() ,
                        "updatedBy",userName,
                        "deleted", true);

        try{
            UpdateResponse updateResponse = client.update(request, RequestOptions.DEFAULT);
            response.setStatus(200);
            response.setMessage("deleted");
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

    public Response deleteAtomicByUuid(String uuid){
        try{
            atomicRepository.deleteById(uuid);
            response.setStatus(200);
            response.setMessage("deleted");
            return response;
        }catch (Exception e){}
        return null;
    }
}
