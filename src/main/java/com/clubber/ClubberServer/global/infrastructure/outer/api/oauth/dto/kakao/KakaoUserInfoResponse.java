package com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.kakao;

import com.clubber.ClubberServer.domain.user.domain.SnsType;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
public record KakaoUserInfoResponse(long id, KakaoAccount kakaoAccount) {

	public User toEntity() {
		return User.builder()
			.email(getEmail())
			.snsType(SnsType.KAKAO)
			.snsId(id)
			.build();
	}

	public String getEmail() {
		return kakaoAccount.email();
	}

	public record KakaoAccount(String email) { }
}
