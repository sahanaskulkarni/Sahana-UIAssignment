package com.example.assignment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
	@Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Assignment")
                        .version("1.0")
                        .description("A retailer offers a rewards program to its customers, awarding points based on each recorded purchase."
                        		+ "Application is used to calculate rewards points earned by customers on each transaction. "
                        		+ "Transactions can be added, deleted and updated for the registered customer. Based on the amount of transaction, reward points are calculated."));

    }
}
