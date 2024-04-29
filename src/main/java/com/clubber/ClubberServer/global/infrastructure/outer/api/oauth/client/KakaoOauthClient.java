package com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.client;

import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.config.KakaoOauthConfig;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.KakaoTokenResponse;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.KakaoUserInfoResponse;
import java.net.URI;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "KakaoAuthClient",
        configuration = KakaoOauthConfig.class
)
public interface KakaoOauthClient {
    @PostMapping
    KakaoTokenResponse kakaoAuth(
            URI baseUrl,
            @RequestParam("grant_type") String grantType,
            @RequestParam("client_id") String restApiKey,
            @RequestParam("redirect_uri") String redirectUrl,
            @RequestParam("code") String code
    );

    @PostMapping
    KakaoUserInfoResponse getUserInfo(
            URI baseUrl,
            @RequestHeader("Authorization") String accessToken
    );

}
