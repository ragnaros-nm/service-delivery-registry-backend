package cl.kintsugi.delivery.service.registry.service;

import cl.kintsugi.delivery.service.registry.models.entity.Coordinator;
import cl.kintsugi.delivery.service.registry.request.CoordinatorRequest;
import java.util.List;

public interface ICoordinatorService{

    List<Coordinator> getAllCoordinators();

    Coordinator findCoordinatorByUuid(String uuid);

    Coordinator saveCoordinator(CoordinatorRequest coordinatorRequest);

    Coordinator updateCoordinator(String uuid, CoordinatorRequest coordinatorRequest);

    Boolean disableCoordinator(String uuid, String userName);

    Boolean deleteCoordinatorByUuid(String uuid);
}
