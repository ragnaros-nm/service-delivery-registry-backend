package cl.kintsugi.delivery.service.registry.repository;

import cl.kintsugi.delivery.service.registry.models.entity.DatapowerDomain;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDatapowerDomainRepository extends ElasticsearchRepository<DatapowerDomain,String> {

    @Query("{\"_source\":{\"includes\":[\"uuid\",\"domainName\",\"deleted\",\"updateDate\"]}}")
    List<DatapowerDomain> findAll();

    DatapowerDomain findDatapowerDomainByUuid(String uuid);

}
