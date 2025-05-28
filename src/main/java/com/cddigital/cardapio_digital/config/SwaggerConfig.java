package com.cddigital.cardapio_digital.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API - Cardápio Digital")
                        .description("Documentação da API de pedidos, produtos e categorias")
                        .version("v1.0"));


    }
}
