package com.clubber.ClubberServer.domain.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateAdminEmailRequest {
    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    @NotNull(message = "인증번호를 입력해주세요")
    private Integer authCode;
}
