package cl.kintsugi.delivery.service.registry.models.entity.commons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Connections {
    private String protocol;
    private String port;
}
