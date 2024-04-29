package com.clubber.ClubberServer.domain.user.service;


import static com.clubber.ClubberServer.global.jwt.JwtProperties.BEARER;

import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.client.KakaoOauthClient;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.KakaoTokenResponse;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.KakaoUserInfoResponse;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.properties.KakaoProperties;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final KakaoOauthClient kakaoOauthClient;

    private final KakaoProperties kakaoProperties;

    public KakaoTokenResponse getToken(String code){

        return kakaoOauthClient.kakaoAuth(
                URI.create(kakaoProperties.getTokenUrl()),
                kakaoProperties.getGrantType(),
                kakaoProperties.getClientId(),
                kakaoProperties.getRedirectUrl(),
                code);
    }

    public KakaoUserInfoResponse getUserKakaoInfo(String accessToken){
        return kakaoOauthClient.getUserInfo(
                URI.create(kakaoProperties.getUserInfoUrl()),
                BEARER + accessToken
        );
    }
}
