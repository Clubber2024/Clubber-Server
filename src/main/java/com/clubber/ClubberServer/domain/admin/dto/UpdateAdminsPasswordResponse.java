package com.clubber.ClubberServer.domain.admin.dto;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateAdminsPasswordResponse {
    private final Long adminId;
    private final String username;

    public static UpdateAdminsPasswordResponse of(Admin admin){
        return UpdateAdminsPasswordResponse.builder()
                .adminId(admin.getId())
                .username(admin.getUsername())
                .build();
    }
}
