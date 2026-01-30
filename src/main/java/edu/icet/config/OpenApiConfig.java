package edu.icet.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Clothing Management System API")
                        .version("1.0.0")
                        .description("API for managing products and categories in the Clothing Management System.")
                        .license(new License().name("MIT")))
                .externalDocs(new ExternalDocumentation().description("Project Repo").url("https://github.com/PaOneX/ThreadLine-csms-api.git"));
    }
}
