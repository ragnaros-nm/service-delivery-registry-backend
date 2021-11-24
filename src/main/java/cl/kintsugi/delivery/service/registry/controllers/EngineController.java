package cl.kintsugi.delivery.service.registry.controllers;

import cl.kintsugi.delivery.service.registry.models.entity.Engine;
import cl.kintsugi.delivery.service.registry.request.EngineRequest;
import cl.kintsugi.delivery.service.registry.service.EngineService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/delivery-service-registry")
public class EngineController {
	
	@Autowired
    private EngineService engineService;

    @GetMapping("/tibco/engine")
    public ResponseEntity<List<Engine>> getAllEngines(@RequestParam(name = "deleted", defaultValue = "false", required = false) Boolean deleted){
        deleted = deleted.equals(null) ? false : deleted;
        if (!engineService.getAllEngines(deleted).isEmpty())
        	return ResponseEntity.status(HttpStatus.OK).body(engineService.getAllEngines(deleted));
        else
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/tibco/engine/{uuid}")
    public ResponseEntity<Engine> findEngineByUuid(@PathVariable(name = "uuid") String uuid){
        if (!engineService.findEngineByUuid(uuid).equals(null))
        	return ResponseEntity.ok(engineService.findEngineByUuid(uuid));
        else
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping("/tibco-engine")
    public ResponseEntity<Engine> saveEngine(@RequestBody EngineRequest engine){
        return ResponseEntity.status(HttpStatus.CREATED).body(engineService.saveEngine(engine));
    }

    @PutMapping("/tibco-engine/{uuid}")
    public ResponseEntity<Engine> updateEngine(@PathVariable(name = "uuid") String uuid,
                                                 @RequestBody EngineRequest request){
        return ResponseEntity.ok(engineService.updateEngine(uuid, request));
    }

    @PatchMapping("/tibco/engine")
    public ResponseEntity<Boolean> disableEngine(@RequestParam(name = "uuid") String uuid,
                                  @RequestParam(name = "userName", required = false) String userName){
    	if (engineService.disableEngine(uuid, userName))
    		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    	else
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    
    @DeleteMapping("/tibco/engine/{uuid}")
    public ResponseEntity<Object> deleteEngine(@PathVariable(name = "uuid") String uuid){
    	if (engineService.deleteEngineByUuid(uuid))
    		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    	else
    	    return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }
}
