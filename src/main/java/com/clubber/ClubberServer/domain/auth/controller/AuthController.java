package com.clubber.ClubberServer.domain.auth.controller;


import com.clubber.ClubberServer.domain.auth.dto.KakaoOauthResponse;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.auth.service.AuthService;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.KakaoTokenResponse;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.KakaoUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
        User user = authService.loginOrSignUp(userKakaoInfo);
        return authService.generateUserToken(user);
    }

    @PostMapping("/refresh")
    public KakaoOauthResponse tokenRefresh(@RequestHeader(value = "token") String refreshToken){
        System.out.println("refreshToken = " + refreshToken);
        return authService.tokenRefresh(refreshToken);
    }

    @PostMapping("/logout")
    public void logOutKakaoUser(){
        authService.logoutKakaoUser();
    }

    @DeleteMapping("/withdraw")
    public void withDrawKakaoUser(){
        authService.withDrawKakaoUser();
    }
}
