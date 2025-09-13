package com.clubber.domain.admin.dto;

import com.clubber.domain.domains.admin.domain.AdminSignupAuth;

public record CreateAdminAuthResponse(
        String email
) {
    public static CreateAdminAuthResponse from(AdminSignupAuth adminSignupAuth) {
        return new CreateAdminAuthResponse(adminSignupAuth.getEmail());
    }
}
