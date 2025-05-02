package com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.kakao;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
public record KakaoTokenResponse(String accessToken,
                                 String refreshToken,
                                 String idToken) {
}