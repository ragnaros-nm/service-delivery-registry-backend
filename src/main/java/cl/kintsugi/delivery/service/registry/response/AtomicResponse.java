package cl.kintsugi.delivery.service.registry.response;

import cl.kintsugi.delivery.service.registry.models.entity.commons.Connections;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AtomicResponse {
    private String uuid;
    private String name;
    @JsonProperty("engine_name")
    private String engineName;
    private String type;
    private String version;
    private String url;
    @JsonProperty("connections")
    private List<Connections> connections;
    private Boolean deleted;
    @JsonProperty("update_date")
    private String updateDate;
}
