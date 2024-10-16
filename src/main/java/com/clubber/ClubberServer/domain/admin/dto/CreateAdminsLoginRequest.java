package com.clubber.ClubberServer.domain.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class CreateAdminsLoginRequest {

    @NotBlank(message = "아이디를 입력해주세요")
    @Schema(description = "동아리 관리자 ID", example = "club1")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Schema(description = "동아리 관리자 비밀번호", example = "123")
    private String password;

    public CreateAdminsLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
