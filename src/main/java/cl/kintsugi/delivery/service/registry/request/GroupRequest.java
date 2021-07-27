package cl.kintsugi.delivery.service.registry.request;

import cl.kintsugi.delivery.service.registry.models.entity.Reference;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@ToString
public class GroupRequest {
    @JsonProperty("keyword")
    private String keyword;
    @JsonProperty("name")
    private String name;
    @JsonProperty("references")
    private List<Reference> references;
    @JsonProperty("projects")
    private boolean deleted;
    private List<String> projects;
    @JsonProperty("updated_by")
    private String updatedBy;
    @JsonProperty("additional_info")
    private String additionalInfo;
}
