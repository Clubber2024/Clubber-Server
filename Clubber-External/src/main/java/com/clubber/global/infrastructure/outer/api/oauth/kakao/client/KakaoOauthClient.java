package com.clubber.global.infrastructure.outer.api.oauth.kakao.client;

import com.clubber.global.config.feign.FeignConfig;
import com.clubber.global.infrastructure.outer.api.oauth.kakao.dto.KakaoOAuthRequest;
import com.clubber.global.infrastructure.outer.api.oauth.kakao.dto.KakaoTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        name = "KakaoAuthClient",
        configuration = FeignConfig.class,
        url = "https://kauth.kakao.com"
)
public interface KakaoOauthClient {

    @PostMapping(value = "/oauth/token?grant_type=authorization_code", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    KakaoTokenResponse kakaoAuth(KakaoOAuthRequest kakaoOAuthRequest);
}
