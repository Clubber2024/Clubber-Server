package com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.kakao;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(SnakeCaseStrategy.class)
public class KakaoTokenResponse {

	private String accessToken;
	private String refreshToken;
	private String idToken;
}