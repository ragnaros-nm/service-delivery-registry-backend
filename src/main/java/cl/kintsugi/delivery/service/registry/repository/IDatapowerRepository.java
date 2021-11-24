package cl.kintsugi.delivery.service.registry.repository;

import cl.kintsugi.delivery.service.registry.models.entity.Datapower;

import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IDatapowerRepository extends ElasticsearchRepository<Datapower,String> {
    Datapower findDatapowerServiceByUuid(String uuid);
    
    List<Datapower> findByDomainName(String domainName);
}
