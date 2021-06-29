package cl.kintsugi.delivery.service.registry.repository;

import cl.kintsugi.delivery.service.registry.models.entity.Group;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IGroupRepository extends ElasticsearchRepository<Group,String> {

    //Busqueda de Group por uuid
    Group findGroupByUuid(String uuid);


    //Busqueda de groups por palabra clave
    @Query("{\"bool\":{\"must\":[{\"match\":{\"keyword\":\"?0\"}}],\"filter\":[{\"term\":{\"deleted\":false }}]}}")
    List<Group> findGroupsByKeyword(String keyword);

    //Eliminacion logica por update (no funca)
    @Query("{\"doc\":{\"deleted\":\"true\"}}")
    Object deleteGroup(String uuid);
}
