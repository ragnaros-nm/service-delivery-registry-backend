package cl.kintsugi.delivery.service.registry.service;

import cl.kintsugi.delivery.service.registry.models.entity.DatapowerDomain;
import cl.kintsugi.delivery.service.registry.models.entity.commons.Connections;
import cl.kintsugi.delivery.service.registry.repository.IDatapowerDomainRepository;
import cl.kintsugi.delivery.service.registry.request.DatapowerDomainRequest;
import cl.kintsugi.delivery.service.registry.response.DomainsResponse;
import cl.kintsugi.delivery.service.registry.response.Response;
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
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;

import cl.kintsugi.delivery.service.registry.service.utils.Formatter;

@Service
public class DatapowerDomainService implements IDatapowerDomainService{
    Logger logger = Logger.getLogger(DatapowerDomainService.class.getName());
    @Autowired
    private IDatapowerDomainRepository datapowerDomainRepository;
    @Autowired
    private Formatter formatter;
    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private Response response;
    private java.lang.Object Object;

    @Override
    public List<DomainsResponse> findAllDatapowerDomains() {
        try {
            SearchRequest searchRequest = new SearchRequest("datapower-domains");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.size(1000);
            String[] includeFields = new String[] {"uuid", "domainName","connections","deleted","updateDate"};
            searchRequest.source(searchSourceBuilder.fetchSource(includeFields, null));
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHit[] searchHits = searchResponse.getHits().getHits();
            List<DomainsResponse> domains = new ArrayList<>();
            for (SearchHit hit : searchHits) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                DomainsResponse domain = new DomainsResponse();
                domain.setUuid((String) sourceAsMap.get("uuid"));
                domain.setDomainName((String) sourceAsMap.get("domainName"));
                domain.setConnections((List<Connections>) sourceAsMap.get("connections"));
                System.out.println(hit.getSourceAsString());
                domain.setDeleted((Boolean) sourceAsMap.get("deleted"));
                domain.setUpdateDate((String) sourceAsMap.get("updateDate"));
                domains.add(domain);
            }
            return domains;
        }catch(Exception e){
            return null;
        }
    }

    public DatapowerDomain findDatapowerDomainByUuid(String uuid) {
        try{
            return datapowerDomainRepository.findDatapowerDomainByUuid(uuid);
        }catch (Exception e){}
        return null;
    }

    @Override
    public DatapowerDomain saveDatapowerDomain(DatapowerDomainRequest domain) {

        DatapowerDomain dp = new DatapowerDomain();
        UUID uuid = UUID.randomUUID();
        try{
            dp.setUuid("gdsd-" + uuid);
            dp.setTechnology("IBM Datapower Gateway");
            dp.setDomainName(domain.getDomainName());
            dp.setMultiProtocolsGateways(domain.getMultiProtocolGateways());
            dp.setConnections(domain.getConnections());
            dp.setRoutingDomainName(domain.getRoutingDomainName());
            dp.setRoutingConnections(domain.getRoutingConnections());
            dp.setServers(domain.getServers());
            dp.setVip(domain.getVip());
            dp.setDns(domain.getDns());
            dp.setRules(domain.getRules());
            dp.setCatalogs(domain.getCatalogs());
            dp.setSslClientProfile(domain.getSslClientProfiles());
            dp.setCreateDate(formatter.getTimeStamp());
            dp.setUpdateDate(formatter.getTimeStamp());
            dp.setUpdatedBy(domain.getUpdatedBy());
            dp.setDeleteDate(null);
            dp.setDeleted(false);
            dp.setAdditionalInfo(domain.getAdditionalInfo());
            return  datapowerDomainRepository.save(dp);
        }catch (Exception e){
            logger.info(e.getMessage());
            return null;
        }
    }

    @Override
    public DatapowerDomain updateDatapowerDomain(DatapowerDomainRequest request, String uuid) {

        try{
            DatapowerDomain domain = datapowerDomainRepository.findDatapowerDomainByUuid(uuid);
            domain.setConnections(request.getConnections());
            domain.setServers(request.getServers());
            domain.setVip(request.getVip());
            domain.setDns(request.getDns());
            domain.setRules(request.getRules());
            domain.setCatalogs(request.getCatalogs());
            domain.setDomainName(request.getDomainName());
            domain.setMultiProtocolsGateways(request.getMultiProtocolGateways());
            domain.setRoutingDomainName(request.getRoutingDomainName());
            domain.setRoutingConnections(request.getRoutingConnections());
            domain.setSslClientProfile(request.getSslClientProfiles());
            domain.setUpdatedBy(request.getUpdatedBy());
            domain.setUpdateDate(formatter.getTimeStamp());
            if(request.isDeleted() && !domain.isDeleted()){
                domain.setDeleteDate(formatter.getTimeStamp());
            }
            if(!request.isDeleted() && domain.isDeleted()){
                domain.setDeleteDate(null);
            }
            domain.setAdditionalInfo(request.getAdditionalInfo());
            return datapowerDomainRepository.save(domain);

        }catch(Exception e){

        }
        return null;
    }

    @Override
    public Response disableDatapowerDomain(String uuid, String userName) {
        logger.info("Eliminando documento de UUID: " + uuid + " por: " + userName);
        if(userName == null){
            userName = "default";
        }
        LocalDateTime actualDate = LocalDateTime.now();
        UpdateRequest request = new UpdateRequest("datapower-domains", uuid)
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

    public Response deleteDatapowerDomainByUuid(String uuid) {
        try{
            datapowerDomainRepository.deleteById(uuid);
            response.setStatus(200);
            response.setMessage("deleted");
            return response;
        }catch (Exception e){}
        return null;
    }
}
