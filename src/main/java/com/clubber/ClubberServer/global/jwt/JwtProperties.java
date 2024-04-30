package com.clubber.ClubberServer.global.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Getter
@Setter
@Component
@ConfigurationProperties("jwt")
public class JwtProperties {
    private String secretKey;
    private Long accessExp;
    private Long refreshExp;
}
