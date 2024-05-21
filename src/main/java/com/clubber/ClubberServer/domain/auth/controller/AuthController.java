package com.clubber.ClubberServer.domain.auth.controller;


import com.clubber.ClubberServer.domain.auth.dto.KakaoOauthResponse;
import com.clubber.ClubberServer.domain.auth.service.helper.CookieHelper;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.auth.service.AuthService;
import com.clubber.ClubberServer.global.config.swagger.DisableSwaggerSecurity;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.KakaoTokenResponse;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.KakaoUserInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
@Tag(name = "[인증]")
public class AuthController {

    private final AuthService authService;

    private final CookieHelper cookieHelper;

    @GetMapping("/oauth/kakao")
    public KakaoOauthResponse getCredentialFromKakao(@RequestParam String code){
        KakaoTokenResponse kakaoToken = authService.getToken(code);
        KakaoUserInfoResponse userKakaoInfo = authService.getUserKakaoInfo(
                kakaoToken.getAccessToken());
        User user = authService.loginOrSignUp(userKakaoInfo);
        return authService.generateUserToken(user);
    }

    @Operation(summary = "토큰 재발급", description = "토큰 만료시 호출 API")
    @PostMapping("/refresh")
    public ResponseEntity<KakaoOauthResponse> tokenRefresh(@RequestHeader(value = "token") String refreshToken){
        KakaoOauthResponse kakaoOauthResponse = authService.tokenRefresh(refreshToken);
        return ResponseEntity.ok()
                .headers(cookieHelper.getCookies(kakaoOauthResponse))
                .body(kakaoOauthResponse);
    }

    @Operation(summary = "카카오 로그아웃")
    @PostMapping("/logout")
    public void logOutKakaoUser(){
        authService.logoutKakaoUser();
    }


    @Operation(summary = "카카오 회원탈퇴")
    @DeleteMapping("/withdraw")
    public void withDrawKakaoUser(){
        authService.withDrawKakaoUser();
    }
}
