package com.edixs.retoindra.infrastructure.documentation;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Reto Indra API")
                        .version("0.0.1-SNAPSHOT")
                        .description("API de ejemplo utilizando Clean Architecture")
                        .contact(new Contact()
                                .name("Edix Carlos")
                                .url("https://edixcarlos.tech/")
                                .email("edix.carlos@tecsup.edu.pe"))
                        .license(new License().name("MIT License").url("https://opensource.org/licenses/MIT")));
    }
}