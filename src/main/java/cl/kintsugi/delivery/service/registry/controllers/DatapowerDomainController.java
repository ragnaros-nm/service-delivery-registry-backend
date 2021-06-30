package cl.kintsugi.delivery.service.registry.controllers;

import cl.kintsugi.delivery.service.registry.models.entity.DatapowerDomain;
import cl.kintsugi.delivery.service.registry.request.DatapowerDomainRequest;
import cl.kintsugi.delivery.service.registry.response.Response;
import cl.kintsugi.delivery.service.registry.service.IDatapowerDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/delivery-service-registry")
public class DatapowerDomainController {

    @Autowired
    private IDatapowerDomainService datapowerDomainService;

    @GetMapping("/datapower-domains")
    public List<DatapowerDomain> getAllDatapowerDomains(){
        return datapowerDomainService.findAllDatapowerDomains();
    }

    @GetMapping("/datapower-domain/{uuid}")
    public DatapowerDomain findDatapwoerDomainByUuid(@PathVariable(name = "uuid") String uuid){
        return datapowerDomainService.findDatapowerDomainByUuid(uuid);
    }

    @PostMapping("/datapower-domain")
    public DatapowerDomain saveDatapowerDomain(@RequestBody DatapowerDomainRequest domain){
        System.out.println(domain.toString());
        return datapowerDomainService.saveDatapowerDomain(domain);
    }

    @PutMapping("/datapower-domain/{uuid}")
    public DatapowerDomain updateDatapowerDomain(@PathVariable(name = "uuid") String uuid,
                                          @RequestBody DatapowerDomainRequest request){
        return datapowerDomainService.updateDatapowerDomain(request,uuid) ;
    }

    @DeleteMapping("/datapower-domain")
    public Response disableDomain(@RequestParam(name = "uuid") String uuid,
                                 @RequestParam(name = "userName", required = false) String userName){
        return datapowerDomainService.disableDatapowerDomain(uuid,userName);
    }

    @DeleteMapping("/datapower-domain/{uuid}")
    public Response deleteDomain(@PathVariable(name = "uuid") String uuid){
        return datapowerDomainService.deleteDatapowerDomainByUuid(uuid);
    }
}
