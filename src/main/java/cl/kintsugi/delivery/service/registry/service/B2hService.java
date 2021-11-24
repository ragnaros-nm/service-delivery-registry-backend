package cl.kintsugi.delivery.service.registry.service;

import cl.kintsugi.delivery.service.registry.models.entity.B2h;
import cl.kintsugi.delivery.service.registry.models.entity.commons.Connections;
import cl.kintsugi.delivery.service.registry.repository.IB2hRepository;
import cl.kintsugi.delivery.service.registry.request.B2hRequest;
import cl.kintsugi.delivery.service.registry.service.utils.Formatter;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
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
public class B2hService implements IB2hService{

    Logger logger = Logger.getLogger(AtomicService.class.getName());
    @Autowired
    private IB2hRepository b2hRepository;
    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private Formatter formatter;
 
    public List<B2h> getAllB2hTransactions(){
        try {

            SearchRequest searchRequest = new SearchRequest("b2h");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.size(1000);
            String[] includeFields = new String[] {"uuid","transaction","connections","url","deleted","updatedBy","updateDate"};
            searchRequest.source(searchSourceBuilder.fetchSource(includeFields, null));
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHit[] searchHits = searchResponse.getHits().getHits();
            List<B2h> b2hList = new ArrayList<>();
            for (SearchHit hit : searchHits) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                B2h b2h = new B2h();
                b2h.setUuid((String) sourceAsMap.get("uuid"));
                b2h.setTransaction((String) sourceAsMap.get("transaction"));
                b2h.setConnections((List<Connections>) sourceAsMap.get("connections"));
                b2h.setUrl((String) sourceAsMap.get("url"));
                b2h.setDeleted((Boolean) sourceAsMap.get("deleted"));
                b2h.setUpdateDate((String) sourceAsMap.get("updateDate"));
                b2h.setUpdatedBy((String) sourceAsMap.get("updatedBy"));
                b2hList.add(b2h);
            }
            return b2hList;
        }catch(Exception e){
            logger.info(e.getMessage());
        	return null;
        }
    }

    public B2h findB2hByUuid(String uuid) {
        return b2hRepository.findB2hByUuid(uuid);
    }

    public B2h saveB2hTransaction(B2hRequest b2hRequest) {
        B2h b2h = new B2h();
        UUID uuid = UUID.randomUUID();
        try{
            b2h.setUuid("b2h-" + uuid);
            b2h.setTechnology("Tibco Businesswork");
            b2h.setType("B2H");
            b2h.setTransaction(b2hRequest.getTransaction());
            b2h.setConnections(b2hRequest.getConnections());
            b2h.setServers(b2hRequest.getServers());
            b2h.setVip(b2hRequest.getVip());
            b2h.setUrl(b2hRequest.getUrl());
            b2h.setRequest(b2hRequest.getRequest());
            b2h.setResponse(b2hRequest.getResponse());
            b2h.setAvailability(b2hRequest.getAvailability());
            b2h.setCreateDate(formatter.getTimeStamp());
            b2h.setUpdateDate(formatter.getTimeStamp());
            b2h.setUpdatedBy(b2hRequest.getUpdatedBy());
            b2h.setDeleteDate(null);
            b2h.setDeleted(false);
            b2h.setAdditionalInfo(b2hRequest.getAdditionalInfo());
            return  b2hRepository.save(b2h);
        }catch (Exception e){
            logger.info(e.getMessage());
            return null;
        }
    }

    public B2h updateB2hTransaction(String uuid, B2hRequest b2hRequest){
        logger.info("UUID: " + uuid);
        B2h b2h = b2hRepository.findB2hByUuid(uuid);
        logger.info(b2h.toString());
        try{
            b2h.setTransaction(b2hRequest.getTransaction());
            b2h.setType("B2h");
            b2h.setConnections(b2hRequest.getConnections());
            b2h.setServers(b2hRequest.getServers());
            b2h.setVip(b2hRequest.getVip());
            b2h.setUrl(b2hRequest.getUrl());
            b2h.setRequest(b2hRequest.getRequest());
            b2h.setResponse(b2hRequest.getResponse());
            b2h.setAvailability(b2hRequest.getAvailability());
            b2h.setUpdateDate(formatter.getTimeStamp());
            b2h.setUpdatedBy(b2hRequest.getUpdatedBy());
            if(b2hRequest.isDeleted() && !b2h.isDeleted()){
                b2h.setDeleteDate(formatter.getTimeStamp());
            }
            if(!b2hRequest.isDeleted() && b2h.isDeleted()){
                b2h.setDeleteDate(null);
            }
            b2h.setDeleted(b2hRequest.isDeleted());
            b2h.setAdditionalInfo(b2hRequest.getAdditionalInfo());
            logger.info(b2h.toString());
            return b2hRepository.save(b2h);
        }
        catch(Exception e){
            logger.info(e.getMessage());
            return null;
        }

    }

    public Boolean disableB2hTransaction(String uuid, String userName){
        logger.info("Eliminando documento de UUID: " + uuid + " por: " + userName);
        if(userName == null){
            userName = "default";
        }
        UpdateRequest request = new UpdateRequest("b2h", uuid)
                .doc("deleteDate",formatter.getTimeStamp() ,
                        "updatedBy",userName,
                        "deleted", true);

        try{
            if (client.update(request, RequestOptions.DEFAULT).status().equals(RestStatus.OK))
            	return true;
            else
            	return false;
        }
        catch(ElasticsearchException e){
        	//TODO implementar capa de errores aquí
            if (e.status() == RestStatus.CONFLICT) {
                System.out.println("Error");
            }
        } catch (IOException e) {
        	//TODO implementar capa de errores aquí
            e.printStackTrace();
        }
        return false;
    }

    public Boolean deleteB2hTransactionByUuid(String uuid){
        try{
        	if (!b2hRepository.findB2hByUuid(uuid).equals(null)) {
        		b2hRepository.deleteById(uuid);
        		return true;
        	}else return false;
     
        }catch (Exception e){
        	//TODO implementar capa de errores aquí
        }
        return false;
    }
}
