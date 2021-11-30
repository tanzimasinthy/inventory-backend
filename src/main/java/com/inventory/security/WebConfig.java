package com.inventory.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("x-auth-token", "Content-Type","x-upload-bucket", "x-upload-token", "x-upload-id", "x-upload-part-number", "x-upload-object-key", "x-upload-content-length", "x-upload-content-type", "authorization", "content-range")
                .exposedHeaders("Content-Type", "x-upload-bucket", "x-upload-token", "x-upload-id", "x-upload-part-number", "x-upload-object-key", "x-upload-content-length", "x-upload-content-type", "authorization", "content-range")
                .allowCredentials(false).maxAge(3600);
    }
}
