package com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.client;


import com.clubber.ClubberServer.domain.auth.dto.UnlinkKaKaoTarget;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.config.KakaoOauthConfig;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.KakaoUserInfoResponse;
import java.net.URI;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "KakaoInfoClient",
        configuration = KakaoOauthConfig.class,
        url = "https://kapi.kakao.com"
)
public interface KakaoInfoClient {
    @PostMapping("v2/user/me")
    KakaoUserInfoResponse getUserInfo(
            @RequestHeader("Authorization") String accessToken
    );

    @PostMapping(path = "v1/user/unlink",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    void unlink(@RequestHeader("Authorization") String adminKey, UnlinkKaKaoTarget kaKaoTarget);

}
