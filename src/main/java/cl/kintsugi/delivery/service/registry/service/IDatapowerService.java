package cl.kintsugi.delivery.service.registry.service;

import cl.kintsugi.delivery.service.registry.models.entity.Datapower;
import cl.kintsugi.delivery.service.registry.request.DatapowerRequest;
import cl.kintsugi.delivery.service.registry.response.DatapowerResponse;
import cl.kintsugi.delivery.service.registry.response.Response;

import java.util.List;

public interface IDatapowerService {

    List<DatapowerResponse> getAllDatapowerServices();

    Datapower findDatapowerServiceByUuid(String uuid);

    Datapower saveDatapowerService(DatapowerRequest datapowerRequest);

    Datapower updateDatapowerService(DatapowerRequest datapowerRequest, String uuid);

    Response disableDatapowerService(String uuid, String userName);

    Response deleteDatapowerServiceByUuid(String uuid);
}
