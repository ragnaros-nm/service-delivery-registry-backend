package cl.kintsugi.delivery.service.registry.repository;

import cl.kintsugi.delivery.service.registry.models.entity.Coordinator;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICoordinatorRepository extends ElasticsearchRepository<Coordinator,String> {

    Coordinator findCoordinatorByUuid(String uuid);
}
