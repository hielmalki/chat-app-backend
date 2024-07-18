package com.example.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author helmalki
 *
 * Configuration class for setting up web-related configurations such as CORS mappings.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configures CORS mappings for the application.
     *
     * @param registry the CORS registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8081")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}
