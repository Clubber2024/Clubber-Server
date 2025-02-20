package com.clubber.ClubberServer.domain.admin.facade;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.domain.AdminEmailAuth;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminAuthResponse;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminMailAuthRequest;
import com.clubber.ClubberServer.domain.admin.service.AdminReadService;
import com.clubber.ClubberServer.domain.admin.service.AdminService;
import com.clubber.ClubberServer.global.infrastructure.outer.mail.MailService;
import com.clubber.ClubberServer.global.util.RandomAuthStringGeneratorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminEmailAuthFacade {

	private final AdminService adminService;
	private final AdminReadService adminReadService;
	private final MailService mailService;
	private final RandomAuthStringGeneratorUtil randomAuthStringGeneratorUtil;

	public CreateAdminAuthResponse createAdminMailAuth(
		CreateAdminMailAuthRequest createAdminMailAuthRequest) {
		String adminEmail = createAdminMailAuthRequest.getEmail();
		Admin admin = adminReadService.getAdminByEmail(adminEmail);

		String authString = randomAuthStringGeneratorUtil.generateRandomMixCharNSpecialChar(10);
		mailService.send("ssuclubber@gmail.com", adminEmail, authString);

		adminService.createAdminMailAuth(authString, adminEmail);
		return new CreateAdminAuthResponse(admin.getId(), admin.getEmail());
	}
}
