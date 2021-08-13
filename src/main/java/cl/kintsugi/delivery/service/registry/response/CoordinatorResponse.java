package cl.kintsugi.delivery.service.registry.response;

import cl.kintsugi.delivery.service.registry.models.entity.commons.Connections;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
@ToString
public class CoordinatorResponse {
    @Id
    private String uuid;
    private String name;
    @JsonProperty("engine_name")
    private String engineName;
    @JsonProperty("engine_version")
    private String engineVersion;
    @JsonProperty("engine_type")
    private String engineType;
    private List<Connections> connections;
    private String url;
    private boolean deleted;
    @JsonProperty("update_date")
    private String updateDate;
}
