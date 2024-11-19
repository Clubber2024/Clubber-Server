package com.clubber.ClubberServer.domain.auth.component;

import com.clubber.ClubberServer.domain.auth.dto.KakaoOauthResponse;
import com.clubber.ClubberServer.domain.auth.service.AuthService;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.client.KakaoInfoClient;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.client.KakaoOauthClient;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.KakaoTokenResponse;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.KakaoUserInfoResponse;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.properties.KakaoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.clubber.ClubberServer.global.jwt.JwtStatic.BEARER;

@Component
@RequiredArgsConstructor
public class UserRegister {
    private final AuthService authService;
    private final KakaoOauthClient kakaoOauthClient;
    private final KakaoInfoClient kakaoInfoClient;
    private final KakaoProperties kakaoProperties;

    public KakaoOauthResponse register(String code, String origin) {
        KakaoTokenResponse kakaoTokenResponse = kakaoOauthClient.kakaoAuth(
                kakaoProperties.getClientId(),
                origin + kakaoProperties.getRedirectUrl(),
                code);

        KakaoUserInfoResponse kakaoUserInfoResponse = kakaoInfoClient.getUserInfo(BEARER + kakaoTokenResponse.getAccessToken());
        User user = authService.loginOrSignUp(kakaoUserInfoResponse);
        return authService.generateUserToken(user);
    }
}
