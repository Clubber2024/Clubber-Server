package com.clubber.domain.auth.facade;

import com.clubber.domain.auth.dto.KakaoOauthResponse;
import com.clubber.domain.auth.service.AuthService;
import com.clubber.global.infrastructure.outer.api.oauth.kakao.client.KakaoInfoClient;
import com.clubber.global.infrastructure.outer.api.oauth.kakao.client.KakaoOauthClient;
import com.clubber.global.infrastructure.outer.api.oauth.kakao.dto.KakaoOAuthRequest;
import com.clubber.global.infrastructure.outer.api.oauth.kakao.dto.KakaoTokenResponse;
import com.clubber.global.infrastructure.outer.api.oauth.kakao.dto.KakaoUserInfoResponse;
import com.clubber.global.properties.KakaoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.clubber.common.consts.ClubberStatic.BEARER;

@Component
@RequiredArgsConstructor
public class UserRegisterFacade {

    private final AuthService authService;
    private final KakaoOauthClient kakaoOauthClient;
    private final KakaoInfoClient kakaoInfoClient;
    private final KakaoProperties kakaoProperties;

    public KakaoOauthResponse register(String code, String origin) {
        String clientId = kakaoProperties.getClientId();
        String redirectUrl = origin + kakaoProperties.getRedirectUrl();

        KakaoOAuthRequest kakaoOAuthRequest = new KakaoOAuthRequest(clientId, redirectUrl, code);
        KakaoTokenResponse kakaoTokenResponse = kakaoOauthClient.kakaoAuth(kakaoOAuthRequest);

        String bearerAccessToken = BEARER + kakaoTokenResponse.accessToken();
        KakaoUserInfoResponse kakaoUserInfoResponse = kakaoInfoClient.getUserInfo(bearerAccessToken);

        return authService.loginOrSignUp(kakaoUserInfoResponse);
    }
}
