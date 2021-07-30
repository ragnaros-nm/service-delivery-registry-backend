package cl.kintsugi.delivery.service.registry.response;

import cl.kintsugi.delivery.service.registry.models.entity.commons.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@ToString
public class DatapowerResponse {
    private String uuid;
    private String name;
    @JsonProperty("domain_name")
    private String domainName;
    @JsonProperty("routing_connections")
    private List<Connections> routingConnections;
    private String url;
    private List<String> features;
    private boolean deleted;
    @JsonProperty("update_date")
    private String updateDate;
}
