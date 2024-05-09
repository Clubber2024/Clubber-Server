package com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties("oauth")
@Component
public class KakaoProperties {

    private String clientId;
    private String redirectUrl;
    private String grantType;
    private String adminKey;
}
