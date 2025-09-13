package com.clubber.domain.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateAdminSignupAuthVerifyRequest {

	private String clubName;

	@NotBlank(message = "인증 수행한 이메일을 입력해주세요.")
	@Schema(description = "인증 수행한 이메일", example = "myclub@gmail.com")
	private String email;

	@NotNull(message = "인증 번호를 입력해주세요")
	@Schema(description = "이메일로 전송 받은 인증 번호")
	private Integer authCode;
}
