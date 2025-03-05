package com.clubber.ClubberServer.domain.admin.facade;

import com.clubber.ClubberServer.domain.admin.domain.AdminSignupAuth;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminAuthResponse;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminMailAuthRequest;
import com.clubber.ClubberServer.domain.admin.dto.GetAdminPasswordFindRequest;
import com.clubber.ClubberServer.domain.admin.repository.AdminRepository;
import com.clubber.ClubberServer.domain.admin.service.AdminEmailAuthService;
import com.clubber.ClubberServer.global.infrastructure.outer.mail.MailService;
import com.clubber.ClubberServer.global.util.RandomAuthCodeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.clubber.ClubberServer.domain.user.domain.AccountState.ACTIVE;

@Component
@RequiredArgsConstructor
public class AdminEmailAuthFacade {

	private final AdminEmailAuthService adminEmailAuthService;
	private final AdminRepository adminRepository;
	private final MailService mailService;

	public CreateAdminAuthResponse createAdminMailAuth(
		CreateAdminMailAuthRequest createAdminMailAuthRequest) {
		final String email = createAdminMailAuthRequest.getEmail();
		Integer authCode = RandomAuthCodeUtil.generateRandomInteger(6);

		mailService.send(email, "[클러버] 회원가입 인증 번호입니다.", authCode.toString());
		AdminSignupAuth adminMailAuth = adminEmailAuthService.createAdminMailAuth(email, authCode);
		return CreateAdminAuthResponse.of(adminMailAuth);
	}

	public void getAdminPasswordFind(CreateAdminMailAuthRequest createAdminMailAuthRequest) {
		final String email = createAdminMailAuthRequest.getEmail();
		if (adminRepository.existsByEmailAndAccountState(email, ACTIVE)) {
			Integer authCode = RandomAuthCodeUtil.generateRandomInteger(6);
			mailService.send(email, "[클러버] 비밀번호 찾기 인증 번호입니다.", authCode.toString());

			adminEmailAuthService.createAdminPasswordFind(email, authCode);
		}
	}
}
