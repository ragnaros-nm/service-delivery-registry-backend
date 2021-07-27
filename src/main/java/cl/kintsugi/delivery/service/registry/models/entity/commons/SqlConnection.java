package cl.kintsugi.delivery.service.registry.models.entity.commons;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SqlConnection {
    private String driver;
    private String url;
    private String user;
}
