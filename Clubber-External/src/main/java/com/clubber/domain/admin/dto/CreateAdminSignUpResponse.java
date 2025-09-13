package com.clubber.domain.admin.dto;

import com.clubber.domain.domains.admin.domain.Contact;
import com.clubber.domain.domains.admin.domain.PendingAdminInfo;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record CreateAdminSignUpResponse(
	String username,
	String clubName,
	String email,
	Contact contact) {

	public static CreateAdminSignUpResponse from(PendingAdminInfo pendingAdminInfo) {
		return CreateAdminSignUpResponse.builder()
			.username(pendingAdminInfo.getUsername())
			.clubName(pendingAdminInfo.getClubName())
			.email(pendingAdminInfo.getEmail())
			.contact(pendingAdminInfo.getContact())
			.build();
	}
}
