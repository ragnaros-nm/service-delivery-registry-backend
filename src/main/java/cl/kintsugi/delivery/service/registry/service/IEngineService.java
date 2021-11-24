package cl.kintsugi.delivery.service.registry.service;

import cl.kintsugi.delivery.service.registry.models.entity.Engine;
import cl.kintsugi.delivery.service.registry.request.EngineRequest;

import java.util.List;

public interface IEngineService {

    List<Engine> getAllEngines(Boolean deleted);

    Engine findEngineByUuid(String uuid);

    Engine saveEngine(EngineRequest engine);

    Engine updateEngine(String uuid, EngineRequest engine);

    Boolean disableEngine(String uuid, String userName);

    Boolean deleteEngineByUuid(String uuid);
}
