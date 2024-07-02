package com.clubber.ClubberServer.domain.admin.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateUsersLoginRequest {
    private String username;
    private String password;

}
