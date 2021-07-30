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
public class DatapowerRequest {
    private String name;
    @JsonProperty("domain_name")
    private String domainName;
    @JsonProperty("multi_protocol_gateway")
    private List<String> multiProtocolsGateway;
    private List<Connections> connections;
    @JsonProperty("routing_domain_name")
    private String routingDomainName;
    @JsonProperty("routing_connections")
    private List<Connections> routingConnections;
    private Servers servers;
    private Environments vip;
    private Environments dns;
    @JsonProperty("deployed_in")
    private EnvironmentStatus deployedIn;
    private EnvironmentStatus availability;
    @JsonProperty("service_catalog")
    private String serviceCatalog;
    private String url;
    @JsonProperty("rule_name")
    private String ruleName;
    private String request ;
    private String response;
    private Context context;
    @JsonProperty("ssl_client_profile")
    private String sslClientProfile;
    private List<String> features;
    private boolean deleted;
    @JsonProperty("updated_by")
    private String updatedBy;
    @JsonProperty("additional_info")
    private String additionalInfo;
}
