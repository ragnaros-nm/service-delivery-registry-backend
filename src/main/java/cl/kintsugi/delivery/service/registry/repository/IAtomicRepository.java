package cl.kintsugi.delivery.service.registry.repository;

import cl.kintsugi.delivery.service.registry.models.entity.Atomic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAtomicRepository extends ElasticsearchRepository<Atomic,String> {

    Atomic findAtomicByUuid(String uuid);
}
