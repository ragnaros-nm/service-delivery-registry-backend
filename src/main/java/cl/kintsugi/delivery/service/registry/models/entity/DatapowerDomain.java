package cl.kintsugi.delivery.service.registry.models.entity;

import cl.kintsugi.delivery.service.registry.models.entity.commons.Connections;
import cl.kintsugi.delivery.service.registry.models.entity.commons.Environments;
import cl.kintsugi.delivery.service.registry.models.entity.commons.Servers;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import java.util.List;

@Getter
@Setter
@ToString
@Document(indexName="datapower-domains")
public class DatapowerDomain {
    @Id
    private String uuid;
    private String technology;
    @JsonProperty("domain_name")
    private String domainName;
    @JsonProperty("multi_protocol_gateways")
    private List<String> multiProtocolsGateways;
    private List<Connections> connections;
    @JsonProperty("routing_domain_name")
    private String routingDomainName;
    @JsonProperty("routing_connections")
    private List<Connections> routingConnections;
    private Servers servers;
    private Environments vip;
    private Environments dns;
    private List<String> rules;
    private List<String> catalogs;
    @JsonProperty("ssl_client_profiles")
    private List<String> SslClientProfile;
    private boolean deleted;
    @JsonProperty("create_date")
    private String createDate;
    @JsonProperty("update_date")
    private String updateDate;
    @JsonProperty("updated_by")
    private String updatedBy;
    @JsonProperty("delete_date")
    private String deleteDate;
    @JsonProperty("additional_info")
    private String additionalInfo;
}
