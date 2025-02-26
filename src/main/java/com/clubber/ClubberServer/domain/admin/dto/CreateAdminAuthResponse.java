package com.clubber.ClubberServer.domain.admin.dto;

public record CreateAdminAuthResponse(
	String email
) {

	public static CreateAdminAuthResponse from(String email) {
		return new CreateAdminAuthResponse(email);
	}
}
