package cl.kintsugi.delivery.service.registry.controllers;

import cl.kintsugi.delivery.service.registry.models.entity.Atomic;
import cl.kintsugi.delivery.service.registry.request.AtomicRequest;
import cl.kintsugi.delivery.service.registry.response.AtomicResponse;
import cl.kintsugi.delivery.service.registry.response.Response;
import cl.kintsugi.delivery.service.registry.service.IAtomicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/delivery-service-registry")
public class AtomicController {

    @Autowired
    private IAtomicService atomicService;

    @GetMapping("/atomics")
    public List<AtomicResponse> getAllAtomics(){
        return atomicService.getAllAtomics();
    }

    @GetMapping("/atomic/{uuid}")
    public Atomic findAtomicByUuid(@PathVariable(name = "uuid") String uuid){
        return atomicService.findAtomicByUuid(uuid);
    }

    @PostMapping("/atomic")
    public Atomic saveAtomic(@RequestBody AtomicRequest atomicRequest){
        System.out.println(atomicRequest.toString());
        return atomicService.saveAtomic(atomicRequest);
    }

   @PutMapping("/atomic/{uuid}")
    public Atomic updateAtomic(@PathVariable(name = "uuid") String uuid,
                               @RequestBody AtomicRequest atomicRequest){
        return atomicService.updateAtomic(uuid, atomicRequest);
    }

    @DeleteMapping("/atomic")
    public Response disableAtomic(@RequestParam(name = "uuid") String uuid,
                                  @RequestParam(name = "userName", required = false) String userName){
        return atomicService.disableAtomic(uuid, userName);
    }

    @DeleteMapping("/atomic/{uuid}")
    public Response deleteAtomic(@PathVariable(name = "uuid") String uuid){
        return atomicService.deleteAtomicByUuid(uuid);
    }
}
