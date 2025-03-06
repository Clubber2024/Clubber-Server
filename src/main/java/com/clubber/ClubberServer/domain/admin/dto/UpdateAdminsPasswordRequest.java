package com.clubber.ClubberServer.domain.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAdminsPasswordRequest {

	@Schema(description = "변경하려는 비밀번호")
	@NotBlank(message = "비밀번호를 입력해주세요")
	private String newPassword;
}
