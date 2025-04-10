package com.flowerstore.flower_shop;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Permite toate cererile de la toate endpoint-urile
                .allowedOrigins("http://localhost:3000") // Permite cereri din frontend-ul de pe localhost:3000
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Permite metodele de cerere
                .allowedHeaders("*") // Permite toate headerele
                .allowCredentials(true); // Permite cookies, dacă este cazul
    }
}
