package com.clubber.ClubberServer.domain.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class CreateAdminUpdateEmailAuthRequest {
    @NotBlank(message = "이메일을 입력해주세요")
    @Schema(description = "변경하려는 이메일")
    private String email;
}
