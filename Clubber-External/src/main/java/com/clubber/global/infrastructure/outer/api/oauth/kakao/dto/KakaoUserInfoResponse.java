package com.clubber.global.infrastructure.outer.api.oauth.kakao.dto;

import com.clubber.domain.domains.user.domain.SnsType;
import com.clubber.domain.domains.user.domain.User;
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

	private String getEmail() {
		return kakaoAccount.email();
	}

	private record KakaoAccount(String email) { }
}
