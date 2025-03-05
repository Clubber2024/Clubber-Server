package com.clubber.ClubberServer.domain.admin.dto;

import com.clubber.ClubberServer.domain.admin.domain.AdminSignupAuth;

public record CreateAdminAuthResponse(
	Long id,
	String email
) {

	public static CreateAdminAuthResponse of(AdminSignupAuth adminSignupAuth) {
		return new CreateAdminAuthResponse(adminSignupAuth.getId(), adminSignupAuth.getEmail());
	}
}
