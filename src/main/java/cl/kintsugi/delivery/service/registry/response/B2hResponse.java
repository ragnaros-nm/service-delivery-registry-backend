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
public class B2hResponse {
    private String uuid;
    private String transaction;
    private List<Connections> connections;
    private String url;
    private boolean deleted;
    @JsonProperty("update_date")
    private String updateDate;
}
