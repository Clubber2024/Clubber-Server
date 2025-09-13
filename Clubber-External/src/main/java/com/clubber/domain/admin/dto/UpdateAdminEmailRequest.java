package com.clubber.domain.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateAdminEmailRequest {
    @Schema(description = "변경하려는 이메일")
    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    @Schema(description = "받은 인증번호")
    @NotNull(message = "인증번호를 입력해주세요")
    private Integer authCode;
}
