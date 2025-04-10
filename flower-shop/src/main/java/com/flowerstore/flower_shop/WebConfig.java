package com.flowerstore.flower_shop;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")// permite accesul la toate endpoint-urile
                .allowedOrigins("http://localhost:3000") //permite cereri din React app
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") //metodel acceptate
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
