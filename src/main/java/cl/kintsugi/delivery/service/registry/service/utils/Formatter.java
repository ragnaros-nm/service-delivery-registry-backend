package cl.kintsugi.delivery.service.registry.service.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class Formatter {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    public String getTimeStamp(){
        LocalDateTime actualDate = LocalDateTime.now();
        return actualDate.format(dateFormatter);
    }
}