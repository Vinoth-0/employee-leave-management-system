package com.assessment.leavemgmt.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Employee Leave Management API")
                        .description("REST APIs for Employee Leave Management System")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Assessment Project")
                                .email("admin@company.com")));
    }
}
