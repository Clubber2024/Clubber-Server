package com.clubber.ClubberServer.domain.auth.facade;

import com.clubber.ClubberServer.domain.auth.dto.KakaoOauthResponse;
import com.clubber.ClubberServer.domain.auth.service.AuthService;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.client.kakao.KakaoInfoClient;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.client.kakao.KakaoOauthClient;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.kakao.KakaoTokenResponse;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.kakao.KakaoUserInfoResponse;
import com.clubber.ClubberServer.global.properties.KakaoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.clubber.ClubberServer.global.common.consts.ClubberStatic.BEARER;

@Component
@RequiredArgsConstructor
public class UserRegisterFacade {

    private final AuthService authService;
    private final KakaoOauthClient kakaoOauthClient;
    private final KakaoInfoClient kakaoInfoClient;
    private final KakaoProperties kakaoProperties;

    public KakaoOauthResponse register(String code, String origin) {
        String redirectUrl = origin + kakaoProperties.getRedirectUrl();
        String clientId = kakaoProperties.getClientId();
        KakaoTokenResponse kakaoTokenResponse = kakaoOauthClient.kakaoAuth(clientId, redirectUrl, code);

        String bearerAccessToken = BEARER + kakaoTokenResponse.getAccessToken();
        KakaoUserInfoResponse kakaoUserInfoResponse = kakaoInfoClient.getUserInfo(bearerAccessToken);

        return authService.loginOrSignUp(kakaoUserInfoResponse);
    }
}
