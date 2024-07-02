package com.clubber.ClubberServer.domain.admin.dto;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUsersLoginResponse {
    private final Long adminId;
    private final String username;
    private final String accessToken;
    private final String refreshToken;

    public static CreateUsersLoginResponse of(Admin admin, String accessToken, String refreshToken){
        return CreateUsersLoginResponse.builder()
                .adminId(admin.getId())
                .username(admin.getUsername())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
