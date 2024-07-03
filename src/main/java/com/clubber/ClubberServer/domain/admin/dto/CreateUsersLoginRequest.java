package com.clubber.ClubberServer.domain.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateUsersLoginRequest {

    @Schema(description = "동아리 관리자 ID", example = "동아리1")
    private String username;

    @Schema(description = "동아리 관리자 비밀번호", example = "123")
    private String password;

}
