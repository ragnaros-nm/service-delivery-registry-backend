package cl.kintsugi.delivery.service.registry.controllers;

import cl.kintsugi.delivery.service.registry.models.entity.Coordinator;
import cl.kintsugi.delivery.service.registry.request.CoordinatorRequest;
import cl.kintsugi.delivery.service.registry.service.CoordinatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/delivery-service-registry")
public class CoordinatorController {

    @Autowired
    private CoordinatorService coordinatorService;

    @GetMapping("/tibco/coordinators")
    public ResponseEntity<List<Coordinator>> getAllCoordinators() {
    	if (!coordinatorService.getAllCoordinators().isEmpty())
    		return ResponseEntity.ok(coordinatorService.getAllCoordinators());
    	else
    		return new ResponseEntity<List<Coordinator>>(HttpStatus.NOT_FOUND);
    };

    @GetMapping("/tibco/coordinators/{uuid}")
    public ResponseEntity<Coordinator> findCoordinatoriByUuid(@PathVariable(name = "uuid") String uuid){
        if (!coordinatorService.findCoordinatorByUuid(uuid).equals(null))
        	return ResponseEntity.ok(coordinatorService.findCoordinatorByUuid(uuid));
        else
        	return new ResponseEntity<Coordinator>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/tibco/coordinators")
    public ResponseEntity<Coordinator> saveCoordinator(@RequestBody CoordinatorRequest coordinatorRequest){
        System.out.println(coordinatorRequest.toString());
        return new ResponseEntity<Coordinator>(
        		coordinatorService.saveCoordinator(coordinatorRequest),
        		HttpStatus.CREATED);
    }

    @PutMapping("/tibco/coordinators/{uuid}")
    public ResponseEntity<Coordinator> updateCoordinator(@PathVariable(name = "uuid") String uuid,
                               @RequestBody CoordinatorRequest coordinatorRequest){
        return ResponseEntity.ok(coordinatorService.updateCoordinator(uuid, coordinatorRequest));
    }

    @PatchMapping("/tibco/coordinators")
    public ResponseEntity<Object> disableCoordinator(@RequestParam(name = "uuid") String uuid,
                                       @RequestParam(name = "userName", required = false) String userName){
        if (coordinatorService.disableCoordinator(uuid, userName))
        	return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        else 
        	return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/tibco/coordinators/{uuid}")
    public ResponseEntity<Object> deleteCoordinator(@PathVariable(name = "uuid") String uuid){
        if (coordinatorService.deleteCoordinatorByUuid(uuid))
        	return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        else
        	return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }
}
