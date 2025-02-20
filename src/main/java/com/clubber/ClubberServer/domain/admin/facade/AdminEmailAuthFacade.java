package com.clubber.ClubberServer.domain.admin.facade;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminAuthResponse;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminMailAuthRequest;
import com.clubber.ClubberServer.domain.admin.service.AdminReadService;
import com.clubber.ClubberServer.domain.admin.service.AdminService;
import com.clubber.ClubberServer.global.util.RandomAuthStringGeneratorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminEmailAuthFacade {

	private final AdminService adminService;
	private final AdminReadService adminReadService;

	public CreateAdminAuthResponse createAdminMailAuth(
		CreateAdminMailAuthRequest createAdminMailAuthRequest) {
		final String adminEmail = createAdminMailAuthRequest.getEmail();
		Admin admin = adminReadService.getAdminByEmail(adminEmail);

		final String authString = RandomAuthStringGeneratorUtil.generateRandomMixCharNSpecialChar(
			10);
		adminService.sendAdminAuthEmail(adminEmail, authString);

		adminService.createAdminMailAuth(authString, adminEmail);
		return new CreateAdminAuthResponse(admin.getId(), admin.getEmail());
	}
}
