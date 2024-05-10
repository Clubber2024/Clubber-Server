package com.clubber.ClubberServer.global.config;


import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.properties.KakaoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({KakaoProperties.class})
@Configuration
public class ConfigurationPropertiesConfig {}
