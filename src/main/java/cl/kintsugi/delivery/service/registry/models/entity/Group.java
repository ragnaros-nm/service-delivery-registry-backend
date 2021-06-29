package cl.kintsugi.delivery.service.registry.models.entity;

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
@Document(indexName="groups")
public class Group {
    @Id
    private String uuid;
    private String keyword;
    private String name;
    private List<Reference> references;
    private List<String> projects;
    //@JsonIgnore
    private boolean deleted;
    @JsonProperty("create_date")
    private String createDate;
    @JsonProperty("update_date")
    private String updateDate;
    @JsonProperty("updated_by")
    private String updatedBy;
    @JsonProperty("delete_date")
    //@JsonIgnore
    private String deleteDate;
    @JsonProperty("additional_info")
    private String additionalInfo;
}
