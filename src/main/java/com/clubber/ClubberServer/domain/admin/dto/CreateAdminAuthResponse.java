package com.clubber.ClubberServer.domain.admin.dto;

import com.clubber.ClubberServer.domain.admin.domain.AdminEmailAuth;

public record CreateAdminAuthResponse(
	Long id,
	String email
) {

	public static CreateAdminAuthResponse of(AdminEmailAuth adminEmailAuth) {
		return new CreateAdminAuthResponse(adminEmailAuth.getId(), adminEmailAuth.getEmail());
	}
}
