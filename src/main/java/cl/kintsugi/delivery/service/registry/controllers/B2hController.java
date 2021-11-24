package cl.kintsugi.delivery.service.registry.controllers;

import cl.kintsugi.delivery.service.registry.models.entity.B2h;
import cl.kintsugi.delivery.service.registry.request.B2hRequest;
import cl.kintsugi.delivery.service.registry.service.B2hService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/delivery-service-registry")
public class B2hController {
    Logger logger = Logger.getLogger(B2hController.class.getName());

    @Autowired
    private B2hService b2hService;

    @GetMapping("/services/b2h")
    public ResponseEntity<List<B2h>> getAllB2hTransactions(){
        if (!b2hService.getAllB2hTransactions().isEmpty())
        	return ResponseEntity.ok(b2hService.getAllB2hTransactions());
        else 
        	return new ResponseEntity<List<B2h>>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/services/b2h/{uuid}")
    public ResponseEntity<B2h> findB2hByUuid(@PathVariable(name = "uuid") String uuid){
        if (!b2hService.findB2hByUuid(uuid).equals(null))
        	return ResponseEntity.ok(b2hService.findB2hByUuid(uuid));
        else
        	return new ResponseEntity<B2h>(HttpStatus.NOT_FOUND);
        		
    }

    @PostMapping("/services/b2h")
    public ResponseEntity<B2h> saveAtomic(@RequestBody B2hRequest b2hRequest){
        logger.info(b2hRequest.toString());
        return new ResponseEntity<B2h>(b2hService.saveB2hTransaction(b2hRequest),HttpStatus.CREATED);
    }

    @PutMapping("/services/b2h/{uuid}")
    public ResponseEntity<B2h> updateAtomic(@PathVariable(name = "uuid") String uuid,
                               @RequestBody B2hRequest b2hRequest){
        return ResponseEntity.ok(b2hService.updateB2hTransaction(uuid, b2hRequest));
    }

    @PatchMapping("/services/b2h")
    public ResponseEntity<Object> disableB2hTransaction(@RequestParam(name = "uuid") String uuid,
                                  @RequestParam(name = "userName", required = false) String userName){
        if (b2hService.disableB2hTransaction(uuid, userName))
        	return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        else
        	return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/services/b2h/{uuid}")
    public ResponseEntity<Object> deleteB2hTransaction(@PathVariable(name = "uuid") String uuid){
    	if (b2hService.deleteB2hTransactionByUuid(uuid))
    		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    	else
    		return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
        
    }
}
