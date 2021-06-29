package cl.kintsugi.delivery.service.registry.models.entity.commons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Servers {
    private List<String> development;
    private List<String> preproduction;
    private List<String> production;
}
