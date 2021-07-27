package cl.kintsugi.delivery.service.registry.request;

import cl.kintsugi.delivery.service.registry.models.entity.commons.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@ToString
public class DatapowerDomainRequest {
    private List<Connections> connections;
    private Servers servers;
    private Environments vip;
    private Environments dns;
    private List<String> rules;
    private List<String> catalogs;
    private boolean deleted;
    @JsonProperty("domain_name")
    private String domainName;
    @JsonProperty("multi_protocol_gateways")
    private List<String> multiProtocolGateways;
    @JsonProperty("routing_domain_name")
    private String routingDomainName;
    @JsonProperty("routing_connections")
    private List<Connections> routingConnections;
    @JsonProperty("ssl_client_profiles")
    private List<String> sslClientProfiles;
    @JsonProperty("updated_by")
    private String updatedBy;
    @JsonProperty("additional_info")
    private String additionalInfo;
}
