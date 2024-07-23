package com.clubber.ClubberServer.global.config.security;


import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        List<String> allowedOriginPatterns = new ArrayList<>();

        allowedOriginPatterns.add("http://localhost:3000");
        allowedOriginPatterns.add("http://13.125.141.171");
        String[] patterns = allowedOriginPatterns.toArray(String[]::new);

        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOriginPatterns(patterns)
                .exposedHeaders("Set-Cookie")
                .allowCredentials(true);
    }
}