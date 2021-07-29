package cl.kintsugi.delivery.service.registry.controllers;

import cl.kintsugi.delivery.service.registry.models.entity.B2h;
import cl.kintsugi.delivery.service.registry.request.B2hRequest;
import cl.kintsugi.delivery.service.registry.response.B2hResponse;
import cl.kintsugi.delivery.service.registry.response.Response;
import cl.kintsugi.delivery.service.registry.service.IB2hService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.logging.Logger;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/delivery-service-registry")
public class B2hController {
    Logger logger = Logger.getLogger(B2hController.class.getName());

    @Autowired
    private IB2hService b2hService;

    @GetMapping("/b2h")
    public List<B2hResponse> getAllB2hTransactions(){
        return b2hService.getAllB2hTransactions();
    }

    @GetMapping("/b2h/{uuid}")
    public B2h findB2hByUuid(@PathVariable(name = "uuid") String uuid){
        return b2hService.findB2hByUuid(uuid);
    }

    @PostMapping("/b2h")
    public B2h saveAtomic(@RequestBody B2hRequest b2hRequest){
        logger.info(b2hRequest.toString());
        return b2hService.saveB2hTransaction(b2hRequest);
    }

    @PutMapping("/b2h/{uuid}")
    public B2h updateAtomic(@PathVariable(name = "uuid") String uuid,
                               @RequestBody B2hRequest b2hRequest){
        return b2hService.updateB2hTransaction(uuid, b2hRequest);
    }

    @DeleteMapping("/b2h")
    public Response disableB2hTransaction(@RequestParam(name = "uuid") String uuid,
                                  @RequestParam(name = "userName", required = false) String userName){
        return b2hService.disableB2hTransaction(uuid, userName);
    }

    @DeleteMapping("/b2h/{uuid}")
    public Response deleteB2hTransaction(@PathVariable(name = "uuid") String uuid){
        return b2hService.deleteB2hTransactionByUuid(uuid);
    }
}
