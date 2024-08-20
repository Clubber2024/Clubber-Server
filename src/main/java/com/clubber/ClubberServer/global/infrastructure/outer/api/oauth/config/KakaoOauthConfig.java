package com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.config;


import feign.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KakaoOauthConfig {

    @Bean
    public Client feignClient() {
        return new Client.Default(null, null);
    }
}
