package com.clubber.domain.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdminPasswordFindRequest {
    @NotBlank(message = "아이디를 입력해주세요")
    private String username;

    @NotBlank(message = "이메일을 입력해주세요")
    private String email;
}
