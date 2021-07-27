package cl.kintsugi.delivery.service.registry.service;

import cl.kintsugi.delivery.service.registry.models.entity.Atomic;
import cl.kintsugi.delivery.service.registry.request.AtomicRequest;
import cl.kintsugi.delivery.service.registry.response.AtomicResponse;
import cl.kintsugi.delivery.service.registry.response.Response;

import java.util.List;

public interface IAtomicService {

    List<AtomicResponse> getAllAtomics();

    Atomic findAtomicByUuid(String uuid);

    Atomic saveAtomic(AtomicRequest atomicRequest);

    Atomic updateAtomic(String uuid, AtomicRequest atomicRequest);

    Response disableAtomic(String uuid, String userName);

    Response deleteAtomicByUuid(String uuid);
}
