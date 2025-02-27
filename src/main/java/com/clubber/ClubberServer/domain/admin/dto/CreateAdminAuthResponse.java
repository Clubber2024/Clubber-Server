package com.clubber.ClubberServer.domain.admin.dto;

import com.clubber.ClubberServer.domain.admin.domain.Admin;

public record CreateAdminAuthResponse(
	Long adminId,
	String email
) {

	public static CreateAdminAuthResponse from(Admin admin) {
		return new CreateAdminAuthResponse(admin.getId(), admin.getEmail());
	}
}
