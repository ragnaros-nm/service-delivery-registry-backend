package cl.kintsugi.delivery.service.registry.controllers;

import cl.kintsugi.delivery.service.registry.models.entity.Atomic;
import cl.kintsugi.delivery.service.registry.request.AtomicRequest;
import cl.kintsugi.delivery.service.registry.service.AtomicService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/delivery-service-registry")
public class AtomicController {

    @Autowired
    private AtomicService atomicService;
    
    
    @GetMapping("/tibco/atomics")
    public ResponseEntity<List<Atomic>> getAllAtomics(){
        if (!atomicService.getAllAtomics().isEmpty())
        	return ResponseEntity.ok(atomicService.getAllAtomics());
        else 
        	return new ResponseEntity<List<Atomic>>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/tibco/atomics/{uuid}")
    public ResponseEntity<Atomic> findAtomicByUuid(@PathVariable(name = "uuid") String uuid){
        if (atomicService.findAtomicByUuid(uuid) != null)
        	return ResponseEntity.ok(atomicService.findAtomicByUuid(uuid));
        else
        	return new ResponseEntity<Atomic>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/tibco/atomics")
    public ResponseEntity<Atomic> saveAtomic(@RequestBody AtomicRequest atomicRequest){
        return new ResponseEntity<Atomic>(atomicService.saveAtomic(atomicRequest),HttpStatus.CREATED);
    }

   @PutMapping("/tibco/atomics/{uuid}")
    public ResponseEntity<Atomic> updateAtomic(@PathVariable(name = "uuid") String uuid,
                               @RequestBody AtomicRequest atomicRequest){
	   
        return ResponseEntity.ok(atomicService.updateAtomic(uuid, atomicRequest));
    }

    @PatchMapping("/tibco/atomics")
    public ResponseEntity<Object> disableAtomic(@RequestParam(name = "uuid") String uuid,
                                  @RequestParam(name = "userName", required = false, defaultValue = "default") String userName){
    	if (atomicService.disableAtomic(uuid,userName))
    		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    	else
    	    return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/tibco/atomics/{uuid}")
    public ResponseEntity<Object> deleteAtomic(@PathVariable(name = "uuid") String uuid){
        if (atomicService.deleteAtomicByUuid(uuid))
        	return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        else
        	return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }
}
