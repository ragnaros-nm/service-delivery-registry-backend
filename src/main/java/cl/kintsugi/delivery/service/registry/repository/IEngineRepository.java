package cl.kintsugi.delivery.service.registry.repository;

import cl.kintsugi.delivery.service.registry.models.entity.Engine;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface IEngineRepository extends ElasticsearchRepository<Engine,String> {

    Engine findEngineByUuid(String uuid);

    void deleteByUuid(String uuid);
}
