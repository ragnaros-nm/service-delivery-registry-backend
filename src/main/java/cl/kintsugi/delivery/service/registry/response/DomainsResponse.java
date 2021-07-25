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
public class DomainsResponse {
    private String uuid;
    @JsonProperty("domain_name")
    private String domainName;
    @JsonProperty("connections")
    private List<Connections> connections;
    private Boolean deleted;
    @JsonProperty("update_date")
    private String updateDate;
}
