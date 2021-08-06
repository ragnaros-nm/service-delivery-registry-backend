package cl.kintsugi.delivery.service.registry.request;

import cl.kintsugi.delivery.service.registry.models.entity.commons.Connections;
import cl.kintsugi.delivery.service.registry.models.entity.commons.Environments;
import cl.kintsugi.delivery.service.registry.models.entity.commons.Servers;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@ToString
public class EngineRequest {

    @JsonProperty("engine_name")
    private String engineName;
    private String version;
    private String type;
    private List<Connections> connections;
    private Servers servers;
    private Environments vip;
    private Environments availability;
    private boolean deleted;
    @JsonProperty("updated_by")
    private String updatedBy;
    @JsonProperty("additional_info")
    private String additionalInfo;
}
