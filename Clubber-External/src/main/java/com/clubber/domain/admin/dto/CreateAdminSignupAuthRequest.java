package com.clubber.domain.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdminSignupAuthRequest {

	private String clubName;

	@NotBlank(message = "전송할 이메일을 입력해주세요")
	@Schema(description = "인증 수행할 이메일", example = "myclub@gmail.com")
	private String email;
}
