package com.clubber.ClubberServer.domain.auth.service.helper;

import com.clubber.ClubberServer.domain.auth.dto.KakaoOauthResponse;
import com.clubber.ClubberServer.global.jwt.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CookieHelper {
    private final JwtProperties jwtProperties;

    public HttpHeaders getCookies(KakaoOauthResponse kakaoOauthResponse){
        ResponseCookie accessToken = ResponseCookie
                .from("accessToken", kakaoOauthResponse.getAccessToken())
                .maxAge(jwtProperties.getAccessExp())
                //.secure(true)
                //.sameSite("Strict")
                .httpOnly(true)
                .path("/")
                .build();

        ResponseCookie refreshToken = ResponseCookie
                .from("refreshToken", kakaoOauthResponse.getRefreshToken())
                .maxAge(jwtProperties.getRefreshExp())
                //.secure(true)
                //.sameSite("None")
                .httpOnly(true)
                .path("/")
                .build();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.SET_COOKIE, accessToken.toString());
        httpHeaders.add(HttpHeaders.SET_COOKIE, refreshToken.toString());
        return httpHeaders;
    }

    public HttpHeaders deleteCookies(){
        ResponseCookie accessToken = ResponseCookie
                .from("accessToken", null)
                .maxAge(0)
                .secure(true)
                .sameSite("Strict")
                .httpOnly(true)
                .path("/")
                .build();

        ResponseCookie refreshToken = ResponseCookie
                .from("refreshToken", null)
                .maxAge(0)
                .secure(true)
                .sameSite("Strict")
                .httpOnly(true)
                .path("/")
                .build();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.SET_COOKIE, accessToken.toString());
        httpHeaders.add(HttpHeaders.SET_COOKIE, refreshToken.toString());
        return httpHeaders;
    }
}
