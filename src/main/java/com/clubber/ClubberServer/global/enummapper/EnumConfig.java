package com.clubber.ClubberServer.global.enummapper;


import com.clubber.ClubberServer.domain.review.domain.Keyword;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnumConfig {

    @Bean
    public EnumMapper getEnumMapper() {
        EnumMapper enumMapper = new EnumMapper();
        return enumMapper;
    }
}
