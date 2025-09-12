package com.clubber.domain.admin.dto;

import com.clubber.domain.admin.domain.Admin;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateAdminsLoginResponse {

	@Schema(description = "관리자 계정 id", example = "1")
	private final Long adminId;

	@Schema(description = "관리자 계정 아이디", example = "club1")
	private final String username;

	@Schema(description = "액세스 토큰")
	private final String accessToken;

	@Schema(description = "리프레시 토큰")
	private final String refreshToken;

	public static CreateAdminsLoginResponse of(Admin admin, String accessToken,
		String refreshToken) {
		return CreateAdminsLoginResponse.builder()
			.adminId(admin.getId())
			.username(admin.getUsername())
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}
}
