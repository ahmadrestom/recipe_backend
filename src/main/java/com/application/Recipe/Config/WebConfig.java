package com.application.Recipe.Config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allows all origins (for development purposes, but can be restrictive for production)
        registry.addMapping("/**")  // This applies CORS to all endpoints
            .allowedOrigins("http://localhost:8080") // Allow specific domains
            .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow specific HTTP methods
            .allowedHeaders("Authorization", "Content-Type") // Allow specific headers (Authorization header for tokens)
            .allowCredentials(true); // Allow sending cookies/credentials (if needed)
    }
}
