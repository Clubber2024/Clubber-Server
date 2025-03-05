package com.clubber.ClubberServer.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdminPasswordFindVerifyRequest {
    private String email;
    private Integer authCode;
}
