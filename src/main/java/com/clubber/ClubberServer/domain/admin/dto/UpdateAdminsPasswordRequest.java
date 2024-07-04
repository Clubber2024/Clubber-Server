package com.clubber.ClubberServer.domain.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateAdminsPasswordRequest {

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;
}
