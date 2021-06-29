package cl.kintsugi.delivery.service.registry.repository;

import cl.kintsugi.delivery.service.registry.models.entity.DatapowerDomain;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDatapowerDomainRepository extends ElasticsearchRepository<DatapowerDomain,String> {

    DatapowerDomain findDatapowerDomainByUuid(String uuid);

}
