package cl.kintsugi.delivery.service.registry.controllers;

import cl.kintsugi.delivery.service.registry.models.entity.Coordinator;
import cl.kintsugi.delivery.service.registry.request.CoordinatorRequest;
import cl.kintsugi.delivery.service.registry.response.CoordinatorResponse;
import cl.kintsugi.delivery.service.registry.response.Response;
import cl.kintsugi.delivery.service.registry.service.ICoordinatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/delivery-service-registry")
public class CoordinatorController {

    @Autowired
    private ICoordinatorService coordinatorService;

    @GetMapping("/coordinators")
    public List<CoordinatorResponse> getAllCoordinators() {
        return coordinatorService.getAllCoordinators();
    }

    @GetMapping("/coordinator/{uuid}")
    public Coordinator findCoordinatoriByUuid(@PathVariable(name = "uuid") String uuid){
        return coordinatorService.findCoordinatorByUuid(uuid);
    }

    @PostMapping("/coordinator")
    public Coordinator saveCoordinator(@RequestBody CoordinatorRequest coordinatorRequest){
        System.out.println(coordinatorRequest.toString());
        return coordinatorService.saveCoordinator(coordinatorRequest);
    }

    @PutMapping("/coordinator/{uuid}")
    public Coordinator updateCoordinator(@PathVariable(name = "uuid") String uuid,
                               @RequestBody CoordinatorRequest coordinatorRequest){
        return coordinatorService.updateCoordinator(uuid, coordinatorRequest);
    }

    @DeleteMapping("/coordinator")
    public Response disableCoordinator(@RequestParam(name = "uuid") String uuid,
                                       @RequestParam(name = "userName", required = false) String userName){
        return coordinatorService.disableCoordinator(uuid, userName);
    }

    @DeleteMapping("/coordinator/{uuid}")
    public Response deleteCoordinator(@PathVariable(name = "uuid") String uuid){
        return coordinatorService.deleteCoordinatorByUuid(uuid);
    }
}
