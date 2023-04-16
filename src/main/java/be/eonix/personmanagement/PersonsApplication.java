package be.eonix.personmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
@OpenAPIDefinition
public class PersonsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonsApplication.class, args);
	}

}
