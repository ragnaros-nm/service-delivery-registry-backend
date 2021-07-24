package cl.kintsugi.delivery.service.registry.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DomainsResponse {
    private String uuid;
    @JsonProperty("domain_name")
    private String domainName;
    private Boolean deleted;
    @JsonProperty("update_date")
    private String updateDate;
}
