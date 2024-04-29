package com.clubber.ClubberServer.domain.auth.controller;


import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.client.KakaoOauthClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auths")
@RequiredArgsConstructor
public class AuthController {

    private final KakaoOauthClient kakaoOauthClient;
    @GetMapping("/oauth/kakao")
    public void getCredentialFromKakao(@RequestParam String code){
    }
}
