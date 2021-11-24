package cl.kintsugi.delivery.service.registry.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "API: Delivery services registry", 
			version = "1.0", 
			description = "Acá encontrarás la documentación de nuestra API para el registro de servicios tanto para: datapower, tibco coordinadores y tibco atómicos."))
public class SwaggerConfig {

	public static void main(String[] args) {
		SpringApplication.run(SwaggerConfig.class, args);
	}

}
