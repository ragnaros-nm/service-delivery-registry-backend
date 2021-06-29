package cl.kintsugi.delivery.service.registry.controllers;
import cl.kintsugi.delivery.service.registry.models.entity.Group;
import cl.kintsugi.delivery.service.registry.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.kintsugi.delivery.service.registry.request.GroupRequest;
import cl.kintsugi.delivery.service.registry.service.IGroupService;

import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "*")
public class GroupController {

    Logger logger = Logger.getLogger(GroupController.class.getName());

    @Autowired
    private IGroupService groupService;
    @Autowired
    private Response response;

    @GetMapping("/group/{uuid}")
    public ResponseEntity<Object> findGroupByUuid(@PathVariable(name = "uuid") String uuid){
        logger.info("Initizalizing method findGroupByUuid()");
        Group group = groupService.findGroupByUuid(uuid);
        if(group == null){
            response.setStatus(404);
            response.setMessage("Not Found");
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(group);
    }

    @GetMapping("/groups")
    public ResponseEntity<Object> findAllGroups(){
        logger.info("Initizalizing method findAllGroups()");
        return ResponseEntity.status(HttpStatus.OK).body(groupService.findAllGroups());
    }

    @PostMapping("/group")
    public ResponseEntity<Group> saveGroup(@RequestBody GroupRequest request){
        logger.info("Initizalizing method saveGroup()");
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.saveGroup(request));
    }

    @PutMapping("/group/{uuid}")
    public ResponseEntity<Group> updateGroup(@RequestBody GroupRequest request, @PathVariable("uuid")String uuid){
        logger.info("Initizalizing method updateGroup()");
        return ResponseEntity.status(HttpStatus.OK).body(groupService.updateGroup(request,uuid));
    }

    @DeleteMapping("/group")
    public ResponseEntity<Object> deleteGroupByUuid(@RequestParam(name = "uuid") String uuid,
                                                    @RequestParam(name = "userName", required = false) String userName) {
        logger.info("Initizalizing method deleteGroupByUuid()");
        return ResponseEntity.status(HttpStatus.OK).body(groupService.deleteGroup(uuid,userName));
    }
}