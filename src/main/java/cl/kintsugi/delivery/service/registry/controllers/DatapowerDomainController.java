package cl.kintsugi.delivery.service.registry.controllers;

import cl.kintsugi.delivery.service.registry.models.entity.DatapowerDomain;
import cl.kintsugi.delivery.service.registry.request.DatapowerDomainRequest;
import cl.kintsugi.delivery.service.registry.response.DomainsResponse;
import cl.kintsugi.delivery.service.registry.service.DatapowerDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/delivery-service-registry")
public class DatapowerDomainController {

    @Autowired
    private DatapowerDomainService datapowerDomainService;

    @GetMapping("/datapower/domains")
    public ResponseEntity<List<DomainsResponse>> getAllDatapowerDomains(){
        if (!datapowerDomainService.findAllDatapowerDomains().isEmpty())
        	return ResponseEntity.ok(datapowerDomainService.findAllDatapowerDomains());
        else
        	return new ResponseEntity<List<DomainsResponse>>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/datapower/domains/{uuid}")
    public ResponseEntity<DatapowerDomain> findDatapowerDomainByUuid(@PathVariable(name = "uuid") String uuid){
        if (!datapowerDomainService.findDatapowerDomainByUuid(uuid).equals(null))
        	return ResponseEntity.ok(datapowerDomainService.findDatapowerDomainByUuid(uuid));
        else
        	return new ResponseEntity<DatapowerDomain>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/datapower/domains")
    public ResponseEntity<DatapowerDomain> saveDatapowerDomain(@RequestBody DatapowerDomainRequest domain){
        return new ResponseEntity<DatapowerDomain>(
        		datapowerDomainService.saveDatapowerDomain(domain),
        		HttpStatus.CREATED);
    }

    @PutMapping("/datapower/domains/{uuid}")
    public ResponseEntity<DatapowerDomain> updateDatapowerDomain(@PathVariable(name = "uuid") String uuid,
                                          @RequestBody DatapowerDomainRequest request){
        return new ResponseEntity<DatapowerDomain>(
        		datapowerDomainService.updateDatapowerDomain(request,uuid),
        		HttpStatus.OK);
    }

    @PatchMapping("/datapower/domains")
    public ResponseEntity<Object> disableDomain(@RequestParam(name = "uuid") String uuid,
                                 @RequestParam(name = "userName", required = false) String userName){
        if(datapowerDomainService.disableDatapowerDomain(uuid,userName))
        	return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        else
        	return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/datapower/domains/{uuid}")
    public ResponseEntity<Object> deleteDomain(@PathVariable(name = "uuid") String uuid){
        if (datapowerDomainService.deleteDatapowerDomainByUuid(uuid))
        	return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        else
        	return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }
}
