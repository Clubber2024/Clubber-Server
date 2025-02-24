package com.clubber.ClubberServer.domain.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateAdminAuthRequest {
	@NotBlank(message = "인증 수행한 이메일을 입력해주세요.")
	@Schema(description = "인증 수행한 이메일", example = "myclub@gmail.com")
	private String email;

	@NotBlank(message = "아이디를 입력해주세요.")
	@Schema(description = "사용하려는 이메일", example = "clubber")
	private String username;

	@NotBlank(message = "인증 번호를 입력해주세요")
	@Schema(description = "이메일로 전송 받은 인증 번호")
	private String authCode;
}
