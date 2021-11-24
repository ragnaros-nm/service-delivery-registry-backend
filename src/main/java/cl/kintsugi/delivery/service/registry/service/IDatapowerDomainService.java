package cl.kintsugi.delivery.service.registry.service;

import cl.kintsugi.delivery.service.registry.models.entity.DatapowerDomain;
import cl.kintsugi.delivery.service.registry.request.DatapowerDomainRequest;
import cl.kintsugi.delivery.service.registry.response.DomainsResponse;
import java.util.List;

public interface IDatapowerDomainService {

    List<DomainsResponse> findAllDatapowerDomains();

    DatapowerDomain findDatapowerDomainByUuid(String uuid);

    DatapowerDomain saveDatapowerDomain(DatapowerDomainRequest domain);

    DatapowerDomain updateDatapowerDomain(DatapowerDomainRequest request, String uuid);

    Boolean disableDatapowerDomain(String uuid, String userName);

    Boolean deleteDatapowerDomainByUuid(String uuid);
}
