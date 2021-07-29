package cl.kintsugi.delivery.service.registry.repository;

import cl.kintsugi.delivery.service.registry.models.entity.B2h;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface IB2hRepository extends ElasticsearchRepository<B2h,String> {

    B2h findB2hByUuid(String uuid);
}
