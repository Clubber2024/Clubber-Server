package com.clubber.ClubberServer.domain.admin.facade;

import com.clubber.ClubberServer.domain.admin.dto.CreateAdminAuthResponse;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminMailAuthRequest;
import com.clubber.ClubberServer.domain.admin.service.AdminEmailAuthService;
import com.clubber.ClubberServer.global.util.RandomAuthStringGeneratorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminEmailAuthFacade {

	private final AdminEmailAuthService adminEmailAuthService;

	public CreateAdminAuthResponse createAdminMailAuth(
		CreateAdminMailAuthRequest createAdminMailAuthRequest) {
		final String email = createAdminMailAuthRequest.getEmail();

		final String authCode = RandomAuthStringGeneratorUtil.generateRandomMixCharNSpecialChar(
			10);
		adminEmailAuthService.sendAdminAuthEmail(email, authCode);
		adminEmailAuthService.createAdminMailAuth(email, authCode);
		return CreateAdminAuthResponse.from(email);
	}
}
