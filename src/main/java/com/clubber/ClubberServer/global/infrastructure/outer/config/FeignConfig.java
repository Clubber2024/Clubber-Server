package com.clubber.ClubberServer.global.infrastructure.outer.config;

import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class FeignConfig {
    //Feign 사용시 TimeOut 설정
    private static final long CONNECTION_TIMEOUT = 10;
    private static final long READ_TIMEOUT = 5;
    @Bean
    public Request.Options requestOptions() {
        return new Request.Options(CONNECTION_TIMEOUT, TimeUnit.SECONDS, READ_TIMEOUT, TimeUnit.SECONDS, false);
    }

    @Bean
    public Retryer retryer() {
        return Retryer.NEVER_RETRY;
    }
}
