package cl.kintsugi.delivery.service.registry.controllers;

import cl.kintsugi.delivery.service.registry.models.entity.Datapower;
import cl.kintsugi.delivery.service.registry.request.DatapowerRequest;
import cl.kintsugi.delivery.service.registry.response.DatapowerResponse;
import cl.kintsugi.delivery.service.registry.response.Response;
import cl.kintsugi.delivery.service.registry.service.IDatapowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/delivery-service-registry")
public class DatapowerController {

    @Autowired
    private IDatapowerService datapowerService;

    @GetMapping("datapower-services")
    public List<DatapowerResponse> getAllDatapowerServices(){
        return datapowerService.getAllDatapowerServices();
    }

    @GetMapping("/datapower-service/{uuid}")
    public Datapower findDatapowerServiceByUuid(@PathVariable(name = "uuid") String uuid){
        return datapowerService.findDatapowerServiceByUuid(uuid);
    }

    @PostMapping("/datapower-service")
    public Datapower saveDatapowerDomain(@RequestBody DatapowerRequest domain){
        System.out.println(domain.toString());
        return datapowerService.saveDatapowerService(domain);
    }

    @PutMapping("/datapower-service/{uuid}")
    public Datapower updateDatapowerDomain(@PathVariable(name = "uuid") String uuid,
                                                 @RequestBody DatapowerRequest request){
        return datapowerService.updateDatapowerService(request,uuid) ;
    }

    @DeleteMapping("/datapower-service")
    public Response disableDomain(@RequestParam(name = "uuid") String uuid,
                                  @RequestParam(name = "userName", required = false) String userName){
        return datapowerService.disableDatapowerService(uuid,userName);
    }

    @DeleteMapping("/datapower-service/{uuid}")
    public Response deleteDomain(@PathVariable(name = "uuid") String uuid){
        return datapowerService.deleteDatapowerServiceByUuid(uuid);
    }
}
