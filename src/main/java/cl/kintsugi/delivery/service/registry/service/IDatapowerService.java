package cl.kintsugi.delivery.service.registry.service;

import cl.kintsugi.delivery.service.registry.models.entity.Datapower;
import cl.kintsugi.delivery.service.registry.request.DatapowerRequest;
import java.util.List;

public interface IDatapowerService {

    List<Datapower> getAllDatapowerServices();

    Datapower findDatapowerServiceByUuid(String uuid);

    Datapower saveDatapowerService(DatapowerRequest datapowerRequest);

    Datapower updateDatapowerService(DatapowerRequest datapowerRequest, String uuid);

    Boolean disableDatapowerService(String uuid, String userName);

    Boolean deleteDatapowerServiceByUuid(String uuid);
}
