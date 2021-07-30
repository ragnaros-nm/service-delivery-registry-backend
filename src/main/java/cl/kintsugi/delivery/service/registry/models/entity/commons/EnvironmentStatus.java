package cl.kintsugi.delivery.service.registry.models.entity.commons;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EnvironmentStatus {
    private boolean development;
    private boolean preproduction;
    private boolean production;
}
