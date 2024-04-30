package com.clubber.ClubberServer.domain.auth.controller;


import com.clubber.ClubberServer.domain.auth.dto.KakaoOauthResponse;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.service.AuthService;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.KakaoTokenResponse;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.KakaoUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auths")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService; 

    @GetMapping("/oauth/kakao")
    public KakaoOauthResponse getCredentialFromKakao(@RequestParam String code){
        KakaoTokenResponse kakaoToken = authService.getToken(code);
        KakaoUserInfoResponse userKakaoInfo = authService.getUserKakaoInfo(
                kakaoToken.getAccessToken());
        User user = authService.loginOrSignUp(userKakaoInfo.getId());
        return authService.generateUserToken(user);
    }
}
