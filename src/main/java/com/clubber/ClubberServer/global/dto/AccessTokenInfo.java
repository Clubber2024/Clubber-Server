package com.clubber.ClubberServer.global.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccessTokenInfo {

	private Long userId;
	private String role;
}
