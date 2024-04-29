package com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.client;

import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.config.KakaoOauthConfig;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.KakaoTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "KakaoAuthClient",
        url = "https://kauth.kakao.com",
        configuration = KakaoOauthConfig.class
)
public interface KakaoOauthClient {
    @PostMapping("/oauth/token?grant_type=authorization_code")
    KakaoTokenResponse kakaoAuth(
            @RequestParam("client_id") String restApiKey,
            @RequestParam("redirect_uri") String redirectUrl,
            @RequestParam("code") String code
    );
}
