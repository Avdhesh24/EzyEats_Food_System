package com.Food_Delivery_System.EzyEats.security;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EzyEats")
                        .version("1.0")
                        .description("API Documentation for EzyEats Food Delivery System"))
                .addServersItem(new Server().url("http://localhost:8080"));
    }
}
