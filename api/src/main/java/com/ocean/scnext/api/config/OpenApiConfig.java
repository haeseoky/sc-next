package com.ocean.scnext.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .components(new Components())
            .info(new Info()
                .title(applicationName)
                .version("1.0.0")
                .description(applicationName + " API"));
    }
}
