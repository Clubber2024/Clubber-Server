package com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.client.kakao;

import com.clubber.ClubberServer.global.config.feign.FeignConfig;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.kakao.KakaoTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
	name = "KakaoAuthClient",
	configuration = FeignConfig.class,
	url = "https://kauth.kakao.com"
)
public interface KakaoOauthClient {

	@PostMapping("/oauth/token?grant_type=authorization_code")
	KakaoTokenResponse kakaoAuth(
		@RequestParam("client_id") String restApiKey,
		@RequestParam("redirect_uri") String redirectUrl,
		@RequestParam("code") String code
	);
}
