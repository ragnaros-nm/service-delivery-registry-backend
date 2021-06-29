package cl.kintsugi.delivery.service.registry.service;

import cl.kintsugi.delivery.service.registry.models.entity.Group;

import java.util.List;

public interface ISearchService {

    List<Group> findGroupsByKeyword(String keyword);
}
