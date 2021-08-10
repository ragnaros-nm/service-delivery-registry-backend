package cl.kintsugi.delivery.service.registry.response;

import cl.kintsugi.delivery.service.registry.models.entity.commons.Connections;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnginesResponse {
    private String uuid;
    @JsonProperty("engine_name")
    private String engineName;
    private String type;
    private String version;
    private Boolean deleted;
    @JsonProperty("update_date")
    private String updateDate;
}
