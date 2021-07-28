package cl.kintsugi.delivery.service.registry.service;

import cl.kintsugi.delivery.service.registry.models.entity.Coordinator;
import cl.kintsugi.delivery.service.registry.request.CoordinatorRequest;
import cl.kintsugi.delivery.service.registry.response.CoordinatorResponse;
import cl.kintsugi.delivery.service.registry.response.Response;
import java.util.List;

public interface ICoordinatorService{

    List<CoordinatorResponse> getAllCoordinators();

    Coordinator findCoordinatorByUuid(String uuid);

    Coordinator saveCoordinator(CoordinatorRequest coordinatorRequest);

    Coordinator updateCoordinator(String uuid, CoordinatorRequest coordinatorRequest);

    Response disableCoordinator(String uuid, String userName);

    Response deleteCoordinatorByUuid(String uuid);
}
