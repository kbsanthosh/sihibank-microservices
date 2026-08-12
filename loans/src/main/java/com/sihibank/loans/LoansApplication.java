package com.sihibank.loans;

import com.sihibank.loans.dto.LoansContactInfoDto;
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
/*@ComponentScans({ @ComponentScan("com.sihibank.loans.controller") })
@EnableJpaRepositories("com.sihibank.loans.repository")
@EntityScan("com.sihibank.loans.model")*/
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {LoansContactInfoDto.class})
@OpenAPIDefinition(
		info = @Info(
				title = "Loans microservice REST API Documentation",
				description = "SihiBank Loans microservice REST API Documentation",
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
				description = "SihiBank Loans microservice REST API Documentation",
				url = "https://www.sihibank.com/swagger-ui.html"
		)
)
public class LoansApplication {

	 static void main(String[] args) {
		SpringApplication.run(LoansApplication.class, args);
	}
}