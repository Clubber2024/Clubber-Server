package com.clubber.ClubberServer.domain.admin.facade;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
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

	public CreateAdminAuthResponse createAdminMailAuth(
		CreateAdminMailAuthRequest createAdminMailAuthRequest) {
		String adminEmail = createAdminMailAuthRequest.getEmail();
		Admin admin = adminReadService.getAdminByEmail(adminEmail);

		String authString = RandomAuthStringGeneratorUtil.generateRandomMixCharNSpecialChar(10);
		mailService.send(adminEmail, "[클러버] 동아리 관리자 계정 인증 번호입니다.", authString);

		adminService.createAdminMailAuth(authString, adminEmail);
		return new CreateAdminAuthResponse(admin.getId(), admin.getEmail());
	}
}
