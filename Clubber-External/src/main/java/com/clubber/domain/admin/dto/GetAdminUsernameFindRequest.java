package com.clubber.domain.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetAdminUsernameFindRequest {
    @NotNull(message = "동아리 id를 입력해주세요")
    private Long clubId;

    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    @NotNull(message = "인증번호를 입력해주세요")
    private Integer authCode;
}
