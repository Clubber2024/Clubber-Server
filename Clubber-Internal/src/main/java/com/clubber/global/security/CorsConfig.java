package com.clubber.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CorsConfig implements WebMvcConfigurer {


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        List<String> allowedOriginPatterns = new ArrayList<>();

        //운영 서버
        allowedOriginPatterns.add("http://localhost:3000");
        allowedOriginPatterns.add("http://localhost:3001");

        String[] patterns = allowedOriginPatterns.toArray(String[]::new);

        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOriginPatterns(patterns)
//                .exposedHeaders("Set-Cookie")
                .allowCredentials(true);
    }
}