package com.sihibank.cards;

import com.sihibank.cards.dto.CardsContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
/*@ComponentScans({ @ComponentScan("com.sihibank.cards.controller") })
@EnableJpaRepositories("com.sihibank.cards.repository")
@EntityScan("com.sihibank.cards.model")*/
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {CardsContactInfoDto.class})
@OpenAPIDefinition(
		info = @Info(
				title = "Cards microservice REST API Documentation",
				description = "SihiBank Cards microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Madan Reddy",
						email = "tutor@sihibank.com",
						url = "https://www.sihibank.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.sihibank.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "SihiBank Cards microservice REST API Documentation",
				url = "https://www.sihibank.com/swagger-ui.html"
		)
)
public class CardsApplication {

	 static void main(String[] args) {
		SpringApplication.run(CardsApplication.class, args);
	}
}
