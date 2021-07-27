package cl.kintsugi.delivery.service.registry.service;

import cl.kintsugi.delivery.service.registry.models.entity.Engine;
import cl.kintsugi.delivery.service.registry.models.entity.commons.Connections;
import cl.kintsugi.delivery.service.registry.repository.IEngineRepository;
import cl.kintsugi.delivery.service.registry.request.EngineRequest;
import cl.kintsugi.delivery.service.registry.response.EnginesResponse;
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
public class EngineService implements IEngineService{

    Logger logger = Logger.getLogger(SearchService.class.getName());
    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private IEngineRepository engineRepository;

    @Autowired
    private Formatter formatter;

    @Autowired
    private Response response;

    @Override
    public List<EnginesResponse> getAllEngines() {
        logger.info("Servicio getAllEmployes");
        SearchRequest searchRequest = new SearchRequest("tibco-engines");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(1000);
        String[] includeFields = new String[] {"uuid","type","version", "engineName","connections","deleted","updateDate"};
        searchRequest.source(searchSourceBuilder.fetchSource(includeFields, null));
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHit[] searchHits = searchResponse.getHits().getHits();
            List<EnginesResponse> engines = new ArrayList<>();
            for (SearchHit hit : searchHits) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                String sourceAsString = hit.getSourceAsString();
                EnginesResponse engine = new EnginesResponse();
                engine.setUuid((String) sourceAsMap.get("uuid"));
                engine.setType((String) sourceAsMap.get("type"));
                engine.setVersion((String) sourceAsMap.get("version"));
                engine.setDeleted((Boolean) sourceAsMap.get("deleted"));
                engine.setEngineName((String) sourceAsMap.get("engineName"));
                engine.setConnections((List<Connections>) sourceAsMap.get("connections"));
                engine.setUpdateDate((String) sourceAsMap.get("updateDate"));
                engines.add(engine);
            }
            logger.warning(engines.toString());
            return engines;
        }catch(Exception e){
            logger.info(e.getMessage());
            return null;
        }
    }

    @Override
    public Engine findEngineByUuid(String uuid) {
        logger.info("UUID: " + uuid);
        try{
            return engineRepository.findEngineByUuid(uuid);
        }catch(Exception e){
            logger.info(e.getMessage());
            return null;
        }
    }

    @Override
    public Engine saveEngine(EngineRequest engine) {

        Engine eng = new Engine();
        UUID uuid = UUID.randomUUID();
        try{
            eng.setUuid("eng-" + uuid);
            eng.setTechnology("Tibco Businesswork");
            eng.setEngineName(engine.getEngineName());
            eng.setConnections(engine.getConnections());
            eng.setVersion(engine.getVersion());
            eng.setType(engine.getType());
            eng.setServers(engine.getServers());
            eng.setVip(engine.getVip());
            eng.setCreateDate(formatter.getTimeStamp());
            eng.setUpdateDate(formatter.getTimeStamp());
            eng.setUpdatedBy(engine.getUpdatedBy());
            eng.setDeleteDate(null);
            eng.setDeleted(false);
            eng.setAdditionalInfo(engine.getAdditionalInfo());
            return  engineRepository.save(eng);
        }catch (Exception e){
            logger.info(e.getMessage());
            return null;
        }
    }

    public Engine updateEngine(String uuid, EngineRequest engineRequest){
        logger.info("UUID: " + uuid);
        Engine eng = engineRepository.findEngineByUuid(uuid);
        logger.info(eng.toString());
        try{
            eng.setEngineName(engineRequest.getEngineName());
            eng.setVersion(engineRequest.getVersion());
            eng.setType(engineRequest.getType());
            eng.setConnections(engineRequest.getConnections());
            eng.setServers(engineRequest.getServers());
            eng.setVip(engineRequest.getVip());
            if(engineRequest.isDeleted() && !eng.isDeleted()){
                eng.setDeleteDate(formatter.getTimeStamp());
            }
            if(!engineRequest.isDeleted() && eng.isDeleted()){
                eng.setDeleteDate(null);
            }
            eng.setUpdateDate(formatter.getTimeStamp());
            eng.setUpdatedBy(engineRequest.getUpdatedBy());
            eng.setAdditionalInfo(engineRequest.getAdditionalInfo());
            logger.info(eng.toString());
            return engineRepository.save(eng);
        }
        catch(Exception e){
            logger.info(e.getMessage());
            return null;
        }
    }

    public Response disableEngine(String uuid, String userName) {
        logger.info("Eliminacion logica de documento con UUID: " + uuid + " por: " + userName);
        if(userName == null){
            userName = "default";
        }
        UpdateRequest request = new UpdateRequest("tibco-engines", uuid)
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

    //Eliminacion permanente
    public Response deleteEngineByUuid(String uuid) {
        try{
            logger.info("Eliminacion permanente...");
            engineRepository.deleteById(uuid);
            response.setStatus(200);
            response.setMessage("deleted");
            return response;
        }catch (Exception e){
            logger.info(e.getMessage());
            return null;
        }
    }
}
