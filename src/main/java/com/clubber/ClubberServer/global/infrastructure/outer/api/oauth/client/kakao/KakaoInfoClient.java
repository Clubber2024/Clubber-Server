package com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.client.kakao;


import com.clubber.ClubberServer.domain.auth.dto.UnlinkKaKaoTarget;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.kakao.KakaoUserInfoResponse;
import com.clubber.ClubberServer.global.config.feign.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "KakaoInfoClient",
        configuration = FeignConfig.class,
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
