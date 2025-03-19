package com.flowerstore.flower_shop;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Permite accesul pentru toate endpoint-urile
                .allowedOrigins("http://localhost:3000")  // Permite cererile din frontend-ul React
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Permite cererile de tip GET, POST, etc.
                .allowedHeaders("*")  // Permite toate tipurile de header-uri
                .allowCredentials(true);  // Permite trimiterea de cookies, etc.
    }
}
