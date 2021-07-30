package cl.kintsugi.delivery.service.registry.service;

import cl.kintsugi.delivery.service.registry.models.entity.Datapower;
import cl.kintsugi.delivery.service.registry.models.entity.commons.Connections;
import cl.kintsugi.delivery.service.registry.repository.IDatapowerRepository;
import cl.kintsugi.delivery.service.registry.request.DatapowerRequest;
import cl.kintsugi.delivery.service.registry.response.DatapowerResponse;
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
public class DatapowerService implements IDatapowerService{
    Logger logger = Logger.getLogger(DatapowerService.class.getName());
    @Autowired
    private IDatapowerRepository datapowerRepository;
    @Autowired
    private Formatter formatter;
    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private Response response;


    public List<DatapowerResponse> getAllDatapowerServices() {
        try {
            SearchRequest searchRequest = new SearchRequest("datapower-services");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.size(1000);
            String[] includeFields = new String[] {"uuid", "name","domainName","routingConnections","url","features","updateDate","deleted"};
            searchRequest.source(searchSourceBuilder.fetchSource(includeFields, null));
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHit[] searchHits = searchResponse.getHits().getHits();
            List<DatapowerResponse> datapowerList = new ArrayList<>();
            for (SearchHit hit : searchHits) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                DatapowerResponse datapower = new DatapowerResponse();
                datapower.setUuid((String) sourceAsMap.get("uuid"));
                datapower.setName((String) sourceAsMap.get("name"));
                datapower.setDomainName((String) sourceAsMap.get("domainName"));
                datapower.setRoutingConnections((List<Connections>) sourceAsMap.get("routingConnections"));
                datapower.setUrl((String) sourceAsMap.get("url"));
                datapower.setFeatures((List<String>) sourceAsMap.get("features"));
                datapower.setDeleted((Boolean) sourceAsMap.get("deleted"));
                datapower.setUpdateDate((String) sourceAsMap.get("updateDate"));
                datapowerList.add(datapower);
            }
            return datapowerList;
        }catch(Exception e){
            return null;
        }
    }

    public Datapower findDatapowerServiceByUuid(String uuid) {
        try{
            return datapowerRepository.findDatapowerServiceByUuid(uuid);
        }catch (Exception e){}
        return null;
    }


    public Datapower saveDatapowerService(DatapowerRequest domain) {

        Datapower dp = new Datapower();
        UUID uuid = UUID.randomUUID();
        try{
            dp.setUuid("gds-" + uuid);
            dp.setTechnology("IBM Datapower Gateway");
            dp.setDomainName(domain.getDomainName());
            dp.setName(domain.getName());
            dp.setMultiProtocolsGateway(domain.getMultiProtocolsGateway());
            dp.setConnections(domain.getConnections());
            dp.setRoutingDomainName(domain.getRoutingDomainName());
            dp.setRoutingConnections(domain.getRoutingConnections());
            dp.setServers(domain.getServers());
            dp.setVip(domain.getVip());
            dp.setDns(domain.getDns());
            dp.setAdditionalInfo(domain.getAdditionalInfo());
            dp.setAvailability(domain.getAvailability());
            dp.setContext(domain.getContext());
            dp.setRequest(domain.getRequest());
            dp.setResponse(domain.getResponse());
            dp.setUrl(domain.getUrl());
            dp.setFeatures(domain.getFeatures());
            dp.setRuleName(domain.getRuleName());
            dp.setServiceCatalog(domain.getServiceCatalog());
            dp.setSslClientProfile(domain.getSslClientProfile());
            dp.setCreateDate(formatter.getTimeStamp());
            dp.setUpdateDate(formatter.getTimeStamp());
            dp.setUpdatedBy(domain.getUpdatedBy());
            dp.setDeleteDate(null);
            dp.setDeleted(false);
            dp.setAdditionalInfo(domain.getAdditionalInfo());
            return  datapowerRepository.save(dp);
        }catch (Exception e){
            logger.info(e.getMessage());
            return null;
        }
    }

    public Datapower updateDatapowerService(DatapowerRequest request, String uuid) {

        try{
            Datapower dp = datapowerRepository.findDatapowerServiceByUuid(uuid);
            dp.setDomainName(request.getDomainName());
            dp.setName(request.getName());
            dp.setMultiProtocolsGateway(request.getMultiProtocolsGateway());
            dp.setConnections(request.getConnections());
            dp.setRoutingDomainName(request.getRoutingDomainName());
            dp.setRoutingConnections(request.getRoutingConnections());
            dp.setServers(request.getServers());
            dp.setVip(request.getVip());
            dp.setDns(request.getDns());
            dp.setAdditionalInfo(request.getAdditionalInfo());
            dp.setAvailability(request.getAvailability());
            dp.setContext(request.getContext());
            dp.setRequest(request.getRequest());
            dp.setResponse(request.getResponse());
            dp.setUrl(request.getUrl());
            dp.setFeatures(request.getFeatures());
            dp.setRuleName(request.getRuleName());
            dp.setServiceCatalog(request.getServiceCatalog());
            dp.setSslClientProfile(request.getSslClientProfile());
            dp.setCreateDate(formatter.getTimeStamp());
            dp.setUpdateDate(formatter.getTimeStamp());
            dp.setUpdatedBy(request.getUpdatedBy());
            dp.setDeleted(request.isDeleted());
            if(request.isDeleted() && !dp.isDeleted()){
                dp.setDeleteDate(formatter.getTimeStamp());
            }
            if(!request.isDeleted() && dp.isDeleted()){
                dp.setDeleteDate(null);
            }
            dp.setAdditionalInfo(request.getAdditionalInfo());
            logger.info(dp.toString());
            return datapowerRepository.save(dp);

        }catch(Exception e){
            return null;
        }
    }

    public Response disableDatapowerService(String uuid, String userName) {
        logger.info("Eliminando documento de UUID: " + uuid + " por: " + userName);
        if(userName == null){
            userName = "default";
        }
        UpdateRequest request = new UpdateRequest("datapower-services", uuid)
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

    public Response deleteDatapowerServiceByUuid(String uuid) {
        try{
            datapowerRepository.deleteById(uuid);
            response.setStatus(200);
            response.setMessage("deleted");
            return response;
        }catch (Exception e){}
        return null;
    }
}
