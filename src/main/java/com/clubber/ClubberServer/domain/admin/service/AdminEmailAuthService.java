package com.clubber.ClubberServer.domain.admin.service;

import com.clubber.ClubberServer.domain.admin.domain.AdminPasswordFindAuth;
import com.clubber.ClubberServer.domain.admin.domain.AdminSignupAuth;
import com.clubber.ClubberServer.domain.admin.dto.GetAdimPasswordFindValidateRequest;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminAuthResponse;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminVerifyEmailAuthRequest;
import com.clubber.ClubberServer.domain.admin.exception.AdminInvalidAuthCodeException;
import com.clubber.ClubberServer.domain.admin.repository.AdminPasswordFindRepository;
import com.clubber.ClubberServer.domain.admin.repository.AdminSignupAuthRepository;
import com.clubber.ClubberServer.domain.admin.validator.AdminValidator;
import com.clubber.ClubberServer.global.infrastructure.outer.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminEmailAuthService {

	private final AdminSignupAuthRepository adminSignupAuthRepository;
	private final AdminPasswordFindRepository adminPasswordFindRepository;
	private final AdminValidator adminValidator;
	private final MailService mailService;

	@Transactional
	public void deleteAdminEmailAuth(AdminSignupAuth adminSignupAuth) {
		adminSignupAuthRepository.delete(adminSignupAuth);
	}

	@Transactional
	public AdminSignupAuth createAdminMailAuth(String email, String authCode) {
		AdminSignupAuth adminSignupAuth = AdminSignupAuth.builder()
			.email(email)
			.authCode(authCode)
			.build();
		return adminSignupAuthRepository.save(adminSignupAuth);
	}

	@Transactional
	public UpdateAdminAuthResponse validateAdminEmailAuth(
		UpdateAdminVerifyEmailAuthRequest updateAdminVerifyEmailAuthRequest) {
		final String authCode = updateAdminVerifyEmailAuthRequest.getAuthCode();
		final String email = updateAdminVerifyEmailAuthRequest.getEmail();
		final Long id = updateAdminVerifyEmailAuthRequest.getId();

		AdminSignupAuth adminSignupAuth = adminSignupAuthRepository.findById(id)
			.orElseThrow(() -> AdminInvalidAuthCodeException.EXCEPTION);

		adminValidator.validateAuthCode(authCode, adminSignupAuth.getAuthCode());
		adminValidator.validateEmail(email, adminSignupAuth.getEmail());
		adminSignupAuth.verify();

		adminSignupAuthRepository.save(adminSignupAuth);
		return new UpdateAdminAuthResponse(email);
	}

	public void sendAdminAuthEmail(String email, String authCode) {
		final String subject = "[클러버] 동아리 관리자 계정 인증 번호입니다.";
		mailService.send(email, subject, authCode);
	}

	@Transactional
	public void saveAdminPasswordFind(String email, Integer authCode) {
		AdminPasswordFindAuth adminPasswordFindAuth = AdminPasswordFindAuth.builder()
				.email(email)
				.authCode(authCode)
				.build();
		adminPasswordFindRepository.save(adminPasswordFindAuth);
	}

	@Transactional(readOnly = true)
	public void validateAdminPasswordFind(GetAdimPasswordFindValidateRequest getAdimPasswordFindValidateRequest) {
		String email = getAdimPasswordFindValidateRequest.getEmail();
		Integer requestAuthCode = getAdimPasswordFindValidateRequest.getAuthCode();
		AdminPasswordFindAuth adminPasswordFindAuth = adminPasswordFindRepository.findById(email)
				.orElseThrow(() -> AdminInvalidAuthCodeException.EXCEPTION);

		if (!adminPasswordFindAuth.getAuthCode().equals(requestAuthCode)) {
			throw AdminInvalidAuthCodeException.EXCEPTION;
		}
	}
}
