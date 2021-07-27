package cl.kintsugi.delivery.service.registry.models.entity.commons;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MQ {
    private MqConnection development;
    private MqConnection preproduction;
    private MqConnection production;
}
