package com.clubber.ClubberServer.domain.admin.dto;

import com.clubber.ClubberServer.domain.admin.domain.PendingAdminInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdminSignUpRequest {

	private String username;

	private String password;

	private String clubName;

	private String email;

	private String contact;

	private String imageForApproval;

	public PendingAdminInfo toEntity(String encodedPassword) {
		return PendingAdminInfo.builder()
			.username(username)
			.password(encodedPassword)
			.clubName(clubName)
			.email(email)
			.contact(contact)
			.imageForApproval(imageForApproval)
			.build();
	}
}
