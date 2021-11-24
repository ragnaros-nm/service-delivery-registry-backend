package cl.kintsugi.delivery.service.registry.service;

import cl.kintsugi.delivery.service.registry.models.entity.Atomic;
import cl.kintsugi.delivery.service.registry.request.AtomicRequest;

import java.util.List;

public interface IAtomicService {

    List<Atomic> getAllAtomics();

    Atomic findAtomicByUuid(String uuid);

    Atomic saveAtomic(AtomicRequest atomicRequest);

    Atomic updateAtomic(String uuid, AtomicRequest atomicRequest);

    Boolean disableAtomic(String uuid, String userName);

    Boolean deleteAtomicByUuid(String uuid);
}
