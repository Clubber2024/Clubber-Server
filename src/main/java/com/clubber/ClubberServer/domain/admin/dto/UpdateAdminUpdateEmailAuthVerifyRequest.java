package com.clubber.ClubberServer.domain.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateAdminUpdateEmailAuthVerifyRequest {
    @NotBlank(message = "이메일을 입력하세요")
    @Schema(description = "변경하려는 이메일 (직전 인증번호 전송된 이메일)")
    private String email;

    @NotNull(message = "인증번호를 입력하세요")
    @Schema(description = "받은 인증번호")
    private Integer authCode;
}
