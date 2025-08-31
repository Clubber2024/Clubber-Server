package com.clubber.ClubberServer.domain.admin.dto;

import com.clubber.ClubberServer.domain.admin.domain.AdminSignupAuth;

public record CreateAdminAuthResponse(
        String email
) {
    public static CreateAdminAuthResponse from(AdminSignupAuth adminSignupAuth) {
        return new CreateAdminAuthResponse(adminSignupAuth.getEmail());
    }
}
