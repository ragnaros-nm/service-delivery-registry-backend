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
public class CoordinatorRequest {
    private String name;
    @JsonProperty("engine_name")
    private String engineName;
    @JsonProperty("engine_version")
    private String engineVersion;
    @JsonProperty("engine_type")
    private String engineType;
    private List<Connections> connections;
    private Servers servers;
    private Environments vip;
    private String url;
    private String request;
    private String response;
    @JsonProperty("deployed_in")
    private Environments deployedIn;
    private Environments availability;
    private boolean deleted;
    @JsonProperty("updated_by")
    private String updatedBy;
    @JsonProperty("additional_info")
    private String additionalInfo;
}