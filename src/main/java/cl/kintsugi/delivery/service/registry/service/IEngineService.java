package cl.kintsugi.delivery.service.registry.service;

import cl.kintsugi.delivery.service.registry.models.entity.Engine;
import cl.kintsugi.delivery.service.registry.request.EngineRequest;
import cl.kintsugi.delivery.service.registry.response.EnginesResponse;
import cl.kintsugi.delivery.service.registry.response.Response;
import org.elasticsearch.common.Nullable;

import java.util.List;

public interface IEngineService {

    List<EnginesResponse> getAllEngines(String deleted);

    Engine findEngineByUuid(String uuid);

    Engine saveEngine(EngineRequest engine);

    Engine updateEngine(String uuid, EngineRequest engine);

    Response disableEngine(String uuid, String userName);

    Response deleteEngineByUuid(String uuid);
}
