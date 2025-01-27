package com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.kakao;

import com.clubber.ClubberServer.domain.user.domain.SnsType;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(SnakeCaseStrategy.class)
public class KakaoUserInfoResponse {

	long id;

	private KakaoAccount kakaoAccount;

	public User toEntity() {
		return User.builder()
			.email(getEmail())
			.snsType(SnsType.KAKAO)
			.snsId(id)
			.build();
	}

	public String getEmail() {
		return kakaoAccount.getEmail();
	}

	@Getter
	@NoArgsConstructor
	@JsonNaming(SnakeCaseStrategy.class)
	public static class KakaoAccount {

		private String email;
	}
}
