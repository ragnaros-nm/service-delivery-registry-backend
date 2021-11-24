package cl.kintsugi.delivery.service.registry.controllers;

import cl.kintsugi.delivery.service.registry.models.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cl.kintsugi.delivery.service.registry.request.GroupRequest;
import cl.kintsugi.delivery.service.registry.service.GroupService;

import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/delivery-service-registry")
public class GroupController {

    Logger logger = Logger.getLogger(GroupController.class.getName());

    @Autowired
    private GroupService groupService;

    @GetMapping("/group/{uuid}")
    public ResponseEntity<Group> findGroupByUuid(@PathVariable(name = "uuid") String uuid){
        logger.info("Initizalizing method findGroupByUuid()");
        if(!groupService.findGroupByUuid(uuid).equals(null))
            return  ResponseEntity.ok(groupService.findGroupByUuid(uuid));
        else 
        	return new ResponseEntity<Group>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/groups")
    public ResponseEntity<Object> findAllGroups(){
        logger.info("Initizalizing method findAllGroups()");
        if (!groupService.findAllGroups().equals(null))
        	return ResponseEntity.status(HttpStatus.OK).body(groupService.findAllGroups());
        else
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping("/group")
    public ResponseEntity<Group> saveGroup(@RequestBody GroupRequest request){
        logger.info("Initizalizing method saveGroup()");
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.saveGroup(request));
    }

    @PutMapping("/group/{uuid}")
    public ResponseEntity<Group> updateGroup(@RequestBody GroupRequest request, @PathVariable("uuid")String uuid){
        logger.info("Initizalizing method updateGroup()");
        return ResponseEntity.ok(groupService.updateGroup(request,uuid));
    }

    @PatchMapping("/group")
    public ResponseEntity<Object> deleteGroupByUuid(@RequestParam(name = "uuid") String uuid,
                                                    @RequestParam(name = "userName", required = false) String userName) {
        logger.info("Initizalizing method deleteGroupByUuid()");
        if (groupService.deleteGroup(uuid,userName))
        	return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        else
        	return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }
}