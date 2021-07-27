package cl.kintsugi.delivery.service.registry.models.entity.commons;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SQL {
    private SqlConnection development;
    private SqlConnection preproduction;
    private SqlConnection production;
}
