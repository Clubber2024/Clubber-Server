package com.clubber.ClubberServer.domain.user.service;


import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.client.KakaoOauthClient;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.KakaoTokenResponse;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.properties.KakaoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final KakaoOauthClient kakaoOauthClient;

    private final KakaoProperties kakaoProperties;

    public void register(String code){
        KakaoTokenResponse kakaoTokenResponse = kakaoOauthClient.kakaoAuth(
                kakaoProperties.getClientId(),
                kakaoProperties.getRedirectUrl(),
                code
        );
    }
}
