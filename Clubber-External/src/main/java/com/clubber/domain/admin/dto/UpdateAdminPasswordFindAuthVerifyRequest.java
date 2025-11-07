package com.clubber.domain.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAdminPasswordFindAuthVerifyRequest {
    @NotBlank(message = "인증 수행한 아이디를 입력해주세요.")
    @Schema(description = "인증 수행한 아이디", example = "clubber")
    private String username;

    @NotNull(message = "인증 번호를 입력해주세요")
    @Schema(description = "이메일로 전송 받은 인증 번호")
    private Integer authCode;
}
