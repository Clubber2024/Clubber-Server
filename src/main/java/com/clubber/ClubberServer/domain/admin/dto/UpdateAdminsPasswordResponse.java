package com.clubber.ClubberServer.domain.admin.dto;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateAdminsPasswordResponse {

    @Schema(description = "동아리 계정 id", example = "1")
    private final Long adminId;

    @Schema(description = "동아리 계정 아이디", example = "club1")
    private final String username;

    public static UpdateAdminsPasswordResponse of(Admin admin){
        return UpdateAdminsPasswordResponse.builder()
                .adminId(admin.getId())
                .username(admin.getUsername())
                .build();
    }
}
