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
		KakaoTokenResponse kakaoTokenResponse = kakaoOauthClient.kakaoAuth(
			kakaoProperties.getClientId(),
			origin + kakaoProperties.getRedirectUrl(),
			code);

		KakaoUserInfoResponse kakaoUserInfoResponse = kakaoInfoClient.getUserInfo(
			BEARER + kakaoTokenResponse.getAccessToken());

		return authService.loginOrSignUp(kakaoUserInfoResponse);
	}
}
