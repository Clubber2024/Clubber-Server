package com.clubber.ClubberServer.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdminPasswordFindRequest {
    private String username;
    private String email;
}
