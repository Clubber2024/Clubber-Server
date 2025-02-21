package com.clubber.ClubberServer.domain.admin.facade;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.domain.AdminEmailAuth;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminAuthResponse;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminMailAuthRequest;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminAuthRequest;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminAuthResponse;
import com.clubber.ClubberServer.domain.admin.service.AdminEmailAuthService;
import com.clubber.ClubberServer.domain.admin.service.AdminReadService;
import com.clubber.ClubberServer.domain.admin.service.AdminService;
import com.clubber.ClubberServer.global.util.RandomAuthStringGeneratorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AdminEmailAuthFacade {

	private final AdminService adminService;
	private final AdminReadService adminReadService;
	private final AdminEmailAuthService adminEmailAuthService;

	public CreateAdminAuthResponse createAdminMailAuth(
		CreateAdminMailAuthRequest createAdminMailAuthRequest) {
		final String adminEmail = createAdminMailAuthRequest.getEmail();
		Admin admin = adminReadService.getAdminByEmail(adminEmail);

		final String authString = RandomAuthStringGeneratorUtil.generateRandomMixCharNSpecialChar(
			10);
		adminService.sendAdminAuthEmail(adminEmail, authString);
		adminEmailAuthService.createAdminMailAuth(adminEmail, authString);
		return new CreateAdminAuthResponse(admin.getId(), admin.getEmail());
	}

	@Transactional
	public UpdateAdminAuthResponse updateAdminAuth(
		UpdateAdminAuthRequest updateAdminAuthRequest) {
		final String requestAuthString = updateAdminAuthRequest.getAuthString();
		final String adminEmail = updateAdminAuthRequest.getAdminEmail();
		final String username = updateAdminAuthRequest.getUsername();

		AdminEmailAuth adminEmailAuth = adminEmailAuthService.validateAdminEmailAuth(adminEmail,
			requestAuthString);

		Admin admin = adminService.updateAdminAccount(adminEmail, username, requestAuthString);
		adminEmailAuthService.deleteAdminEmailAuth(adminEmailAuth);
		return new UpdateAdminAuthResponse(admin.getId());
	}
}
