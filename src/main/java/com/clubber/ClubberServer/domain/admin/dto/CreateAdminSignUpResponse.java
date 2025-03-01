package com.clubber.ClubberServer.domain.admin.dto;

import com.clubber.ClubberServer.domain.admin.domain.PendingAdminInfo;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record CreateAdminSignUpResponse(
	String username,
	String clubName,
	String email,
	String contact) {

	public static CreateAdminSignUpResponse from(PendingAdminInfo pendingAdminInfo) {
		return CreateAdminSignUpResponse.builder()
			.username(pendingAdminInfo.getUsername())
			.clubName(pendingAdminInfo.getClubName())
			.email(pendingAdminInfo.getEmail())
			.contact(pendingAdminInfo.getContact())
			.build();
	}
}
