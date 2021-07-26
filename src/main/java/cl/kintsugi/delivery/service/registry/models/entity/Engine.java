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
@Document(indexName="tibco-engines")
public class Engine {
    @Id
    private String uuid;
    private String technology;
    @JsonProperty("engine_name")
    private String engineName;
    private String version;
    private String type;
    private List<Connections> connections;
    private Servers servers;
    private Environments vip;
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
