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
import org.springframework.web.bind.annotation.CookieValue;
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
    @DisableSwaggerSecurity
    public ResponseEntity getCredentialFromKakao(@RequestParam String code){
        KakaoTokenResponse kakaoToken = authService.getToken(code);
        KakaoUserInfoResponse userKakaoInfo = authService.getUserKakaoInfo(
                kakaoToken.getAccessToken());
        User user = authService.loginOrSignUp(userKakaoInfo);
        KakaoOauthResponse kakaoOauthResponse = authService.generateUserToken(user);
        return ResponseEntity.ok()
                .headers(cookieHelper.getCookies(kakaoOauthResponse))
                .body(kakaoOauthResponse);
    }

    @Operation(summary = "토큰 재발급", description = "토큰 만료시 호출 API")
    @PostMapping("/refresh")
    @DisableSwaggerSecurity
    public ResponseEntity<KakaoOauthResponse> tokenRefresh(
            @CookieValue(value = "refreshToken", required = false) String refreshTokenCookie,
            @RequestHeader(value = "token", required = false, defaultValue = "") String refreshToken){

        KakaoOauthResponse kakaoOauthResponse = authService.tokenRefresh(
                refreshTokenCookie != null ? refreshTokenCookie : refreshToken);
        return ResponseEntity.ok()
                .headers(cookieHelper.getCookies(kakaoOauthResponse))
                .body(kakaoOauthResponse);
    }

    @Operation(summary = "카카오 로그아웃")
    @PostMapping("/logout")
    public ResponseEntity logOutKakaoUser(){

        authService.logoutKakaoUser();
        return ResponseEntity.ok()
                .headers(cookieHelper.deleteCookies())
                .body(null);
    }


    @Operation(summary = "카카오 회원탈퇴")
    @DeleteMapping("/withdraw")
    public ResponseEntity withDrawKakaoUser(){

        authService.withDrawKakaoUser();
        return ResponseEntity.ok()
                .headers(cookieHelper.deleteCookies())
                .body(null);
    }
}
