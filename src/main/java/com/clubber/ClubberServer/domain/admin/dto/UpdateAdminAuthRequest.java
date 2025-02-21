package com.clubber.ClubberServer.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateAdminAuthRequest {
	private String email;
	private String username;
	private String authCode;
}
