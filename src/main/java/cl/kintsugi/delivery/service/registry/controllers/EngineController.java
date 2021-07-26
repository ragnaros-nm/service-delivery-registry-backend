package cl.kintsugi.delivery.service.registry.controllers;

import cl.kintsugi.delivery.service.registry.models.entity.Engine;
import cl.kintsugi.delivery.service.registry.request.EngineRequest;
import cl.kintsugi.delivery.service.registry.response.EnginesResponse;
import cl.kintsugi.delivery.service.registry.response.Response;
import cl.kintsugi.delivery.service.registry.service.IEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/delivery-service-registry")
public class EngineController {

    @Autowired
    private IEngineService engineService;

    @GetMapping("/engines")
    public List<EnginesResponse> getAllEngines(){
        return engineService.getAllEngines();
    }

    @GetMapping("/engine/{uuid}")
    public Engine findEngineByUuid(@PathVariable(name = "uuid") String uuid){
        return engineService.findEngineByUuid(uuid);
    }

    @PostMapping("/engine")
    public Engine saveEngine(@RequestBody EngineRequest engine){
        System.out.println(engine.toString());
        return engineService.saveEngine(engine);
    }

    @PutMapping("/engine/{uuid}")
    public Engine updateEngine(@PathVariable(name = "uuid") String uuid,
                                                 @RequestBody EngineRequest request){
        return engineService.updateEngine(uuid, request);
    }

    @DeleteMapping("/engine")
    public Response disableEngine(@RequestParam(name = "uuid") String uuid,
                                  @RequestParam(name = "userName", required = false) String userName){
        return engineService.disableEngine(uuid, userName);
    }

    @DeleteMapping("/engine/{uuid}")
    public Response deleteEngine(@PathVariable(name = "uuid") String uuid){
        return engineService.deleteEngineByUuid(uuid);
    }
}
