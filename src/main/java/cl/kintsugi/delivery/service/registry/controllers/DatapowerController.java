package cl.kintsugi.delivery.service.registry.controllers;

import cl.kintsugi.delivery.service.registry.models.entity.Datapower;
import cl.kintsugi.delivery.service.registry.request.DatapowerRequest;
import cl.kintsugi.delivery.service.registry.service.DatapowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
@RequestMapping("/api/v1/delivery-service-registry")
public class DatapowerController {

    @Autowired
    private DatapowerService datapowerService;
    
    @GetMapping("/datapower/servicesByDomain/{domainName}")
    public ResponseEntity<List<Datapower>> getServiceByDomainName(@PathVariable("domainName") String domainName){
    	if (!datapowerService.findByDomainName(domainName).isEmpty())
    		return ResponseEntity.ok(datapowerService.findByDomainName(domainName));
    	else
    		return new ResponseEntity<List<Datapower>>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/datapower/services")
    public ResponseEntity<List<Datapower>> getAllDatapowerServices(){
    	if (!datapowerService.getAllDatapowerServices().isEmpty())
    		return ResponseEntity.ok(datapowerService.getAllDatapowerServices());
    	else
    		return new ResponseEntity<List<Datapower>>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/datapower/services/{uuid}")
    public ResponseEntity<Datapower> findDatapowerServiceByUuid(@PathVariable("uuid") String uuid){
        if (!datapowerService.findDatapowerServiceByUuid(uuid).equals(null))
        	return ResponseEntity.ok(datapowerService.findDatapowerServiceByUuid(uuid));
        else
        	return new ResponseEntity<Datapower>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/datapower/services")
    public ResponseEntity<Datapower> saveDatapowerDomain(@RequestBody DatapowerRequest domain){
        return new ResponseEntity<Datapower>(
        		datapowerService.saveDatapowerService(domain),
        		HttpStatus.CREATED);
    }

    @PutMapping("/datapower/services/{uuid}")
    public ResponseEntity<Datapower> updateDatapowerDomain(@PathVariable(name = "uuid") String uuid,
                                                 @RequestBody DatapowerRequest request){
        return ResponseEntity.ok(datapowerService.updateDatapowerService(request,uuid));
    }

    @PatchMapping("/datapower/services")
    public ResponseEntity<Object> disableDomain(@RequestParam(name = "uuid") String uuid,
                                  @RequestParam(name = "userName", required = false) String userName){
        if (datapowerService.disableDatapowerService(uuid,userName))
        	return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        else return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/datapower/services/{uuid}")
    public ResponseEntity<Object> deleteDomain(@PathVariable(name = "uuid") String uuid){
    	if (datapowerService.deleteDatapowerServiceByUuid(uuid))
    		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    	else return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }
}
