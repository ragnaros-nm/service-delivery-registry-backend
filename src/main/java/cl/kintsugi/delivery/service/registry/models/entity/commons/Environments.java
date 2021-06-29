package cl.kintsugi.delivery.service.registry.models.entity.commons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class Environments {
    private String development;
    private String preproduction;
    private String production;
}
