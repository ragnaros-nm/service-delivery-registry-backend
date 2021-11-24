package cl.kintsugi.delivery.service.registry.service;

import java.util.List;

import cl.kintsugi.delivery.service.registry.models.entity.Group;
import cl.kintsugi.delivery.service.registry.request.GroupRequest;

public interface IGroupService {

    List<Group> findAllGroups();

    Group findGroupByUuid(String uuid);

    Group saveGroup(GroupRequest request);

    Group updateGroup(GroupRequest request, String uuid);

    Boolean deleteGroup(String uuid, String userName);
    
    //List<Group> findGroupsByKeyword(String keyword);

}
