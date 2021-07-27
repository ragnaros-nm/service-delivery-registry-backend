package cl.kintsugi.delivery.service.registry.models.entity.commons;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MqConnection {
    private String server;
    private String channel;
    private String qm;
    private String port;
}
