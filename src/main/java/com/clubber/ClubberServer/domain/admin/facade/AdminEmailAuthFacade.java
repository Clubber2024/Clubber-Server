package com.clubber.ClubberServer.domain.admin.facade;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.domain.AdminEmailAuth;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminAuthResponse;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminMailAuthRequest;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminAuthRequest;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminAuthResponse;
import com.clubber.ClubberServer.domain.admin.service.AdminAccountService;
import com.clubber.ClubberServer.domain.admin.service.AdminEmailAuthService;
import com.clubber.ClubberServer.domain.admin.service.AdminReadService;
import com.clubber.ClubberServer.global.util.RandomAuthStringGeneratorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AdminEmailAuthFacade {

	private final AdminEmailAuthService adminEmailAuthService;
	private final AdminAccountService adminAccountService;

	public CreateAdminAuthResponse createAdminMailAuth(
		CreateAdminMailAuthRequest createAdminMailAuthRequest) {
		final String email = createAdminMailAuthRequest.getEmail();

		final String authCode = RandomAuthStringGeneratorUtil.generateRandomMixCharNSpecialChar(
			10);
		adminEmailAuthService.sendAdminAuthEmail(email, authCode);
		adminEmailAuthService.createAdminMailAuth(email, authCode);
		return CreateAdminAuthResponse.from(email);
	}

	@Transactional
	public UpdateAdminAuthResponse updateAdminAuth(
		UpdateAdminAuthRequest updateAdminAuthRequest) {
		final String authCode = updateAdminAuthRequest.getAuthCode();
		final String email = updateAdminAuthRequest.getEmail();
		final String username = updateAdminAuthRequest.getUsername();

		AdminEmailAuth adminEmailAuth = adminEmailAuthService.validateAdminEmailAuth(email,
			authCode);

		Admin admin = adminAccountService.updateAdminAccountWithAuthCode(email, username, authCode);
		adminEmailAuthService.deleteAdminEmailAuth(adminEmailAuth);
		return new UpdateAdminAuthResponse(admin.getId());
	}
}
