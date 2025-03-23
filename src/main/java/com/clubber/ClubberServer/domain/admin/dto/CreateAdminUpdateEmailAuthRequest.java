package com.clubber.ClubberServer.domain.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class CreateAdminUpdateEmailAuthRequest {
    @NotBlank(message = "이메일을 입력해주세요")
    private String email;
}
