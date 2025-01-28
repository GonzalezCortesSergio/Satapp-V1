package com.salesianostriana.dam.satapp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				description = "API de gestión de incidencias relacionadas con tecnología",
				version = "1.0",
				contact = @Contact(
						email = "gonzalez.coser24@triana.salesianos.edu," +
								" alonso.aypat22@triana.salesianos.edu",
						name = "Sergio, Patty"
				),
				title = "Satapp"
		)
)
public class SatappApplication {

	public static void main(String[] args) {
		SpringApplication.run(SatappApplication.class, args);
	}

}
