package cl.kintsugi.delivery.service.registry.service;

import cl.kintsugi.delivery.service.registry.models.entity.Group;
import cl.kintsugi.delivery.service.registry.service.utils.Formatter;
import org.assertj.core.util.Lists;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.kintsugi.delivery.service.registry.repository.IGroupRepository;
import cl.kintsugi.delivery.service.registry.request.GroupRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class GroupService implements IGroupService {

    Logger logger = Logger.getLogger(GroupService.class.getName());
    @Autowired
    private Formatter formatter;
    @Autowired
    private IGroupRepository groupRepository;
    @Autowired
    private RestHighLevelClient client;

    public List<Group> findAllGroups() {
        try {
        	return Lists.newArrayList(groupRepository.findAll());
            
        }
        catch(Exception e) {
            logger.info(e.getMessage());
            return null;
        }
    }

    public Group findGroupByUuid(String uuid) {
        try{
            return groupRepository.findGroupByUuid(uuid);
        }catch(Exception e){
            return null;
        }

    }

    public Group saveGroup(GroupRequest request) {
        logger.info(request.toString());
        LocalDateTime actualDate = LocalDateTime.now();
        UUID uuid = UUID.randomUUID();
        Group group = new Group();
        try{
            group.setUuid("group-"+uuid);
            group.setKeyword(request.getKeyword());
            group.setName(request.getName());
            group.setReferences(request.getReferences());
            group.setProjects(request.getProjects());
            group.setCreateDate(formatter.getTimeStamp());
            group.setUpdateDate(formatter.getTimeStamp());
            group.setUpdatedBy(request.getUpdatedBy());
            group.setDeleted(false);
            group.setDeleteDate(null);
            group.setAdditionalInfo(request.getAdditionalInfo());
            return groupRepository.save(group);
        }catch (Exception e){
            logger.info(e.getMessage());
            return null;
        }
    }

    @Override
    public Group updateGroup(GroupRequest request, String uuid) {
        Group group = findGroupByUuid(uuid);
        group.setUuid(uuid);
        group.setKeyword(request.getKeyword());
        group.setName(request.getName());
        group.setReferences(request.getReferences());
        group.setProjects(request.getProjects());
        if(request.isDeleted() && !group.isDeleted()){
            group.setDeleteDate(formatter.getTimeStamp());
        }
        if(!request.isDeleted() && group.isDeleted()){
            group.setDeleteDate(null);
        }
        group.setUpdateDate(formatter.getTimeStamp());
        group.setUpdatedBy(request.getUpdatedBy());
        group.setAdditionalInfo(request.getAdditionalInfo());

        logger.info("Updating " + uuid + " with values: " + group.toString());

        return groupRepository.save(group);
    }

    public Boolean deleteGroup(String uuid, String userName){

        logger.info("Eliminando documento de UUID: " + uuid + " por: " + userName);
        if(userName == null){
            userName = "default";
        }
        LocalDateTime actualDate = LocalDateTime.now();
        UpdateRequest request = new UpdateRequest("groups", uuid)
                .doc("deleteDate", formatter.getTimeStamp(),
                        "updatedBy",userName,
                        "deleted", true);

        try{
            if(client.update(request, RequestOptions.DEFAULT).status().equals(RestStatus.OK))
            	return true;
            else
            	return false;
        }
        catch(ElasticsearchException e){
            if (e.status() == RestStatus.CONFLICT) {
                System.out.println("Error");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
