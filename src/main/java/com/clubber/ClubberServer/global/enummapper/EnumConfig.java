package com.clubber.ClubberServer.global.enummapper;


import com.clubber.ClubberServer.domain.club.domain.*;
import com.clubber.ClubberServer.domain.review.domain.Keyword;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnumConfig {

    @Bean
    public EnumMapper getEnumMapper() {
        EnumMapper enumMapper = new EnumMapper();
        enumMapper.put("Keyword", Keyword.class);
        enumMapper.put("Hashtag", Hashtag.class);
        enumMapper.put("Division", Division.class);
        enumMapper.put("Department", Department.class);
        enumMapper.put("College", College.class);
        enumMapper.put("ClubType", ClubType.class);
        return enumMapper;
    }
}
