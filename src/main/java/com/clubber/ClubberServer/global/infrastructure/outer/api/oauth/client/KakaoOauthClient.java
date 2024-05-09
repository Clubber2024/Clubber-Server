package com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.client;

import com.clubber.ClubberServer.domain.auth.dto.UnlinkKaKaoTarget;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.config.KakaoOauthConfig;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.KakaoTokenResponse;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.KakaoUserInfoResponse;
import java.net.URI;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "KakaoAuthClient",
        configuration = KakaoOauthConfig.class,
        url = "https://kauth.kakao.com"
)
public interface KakaoOauthClient {
    @PostMapping("/oauth/token")
    KakaoTokenResponse kakaoAuth(
            @RequestParam("grant_type") String grantType,
            @RequestParam("client_id") String restApiKey,
            @RequestParam("redirect_uri") String redirectUrl,
            @RequestParam("code") String code
    );
}
