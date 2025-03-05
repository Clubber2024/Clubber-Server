package com.clubber.ClubberServer.domain.admin.service;

import com.clubber.ClubberServer.domain.admin.domain.AdminPasswordFindAuth;
import com.clubber.ClubberServer.domain.admin.domain.AdminSignupAuth;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminPasswordFindVerifyRequest;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminValidateSignupAuthRequest;
import com.clubber.ClubberServer.domain.admin.exception.AdminInvalidAuthCodeException;
import com.clubber.ClubberServer.domain.admin.repository.AdminPasswordFindRepository;
import com.clubber.ClubberServer.domain.admin.repository.AdminSignupAuthRepository;
import com.clubber.ClubberServer.domain.admin.validator.AdminValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminEmailAuthService {

	private final AdminSignupAuthRepository adminSignupAuthRepository;
	private final AdminPasswordFindRepository adminPasswordFindRepository;
	private final AdminValidator adminValidator;

	@Transactional
	public AdminSignupAuth createAdminSignupAuth(String email, Integer authCode) {
		AdminSignupAuth adminSignupAuth = AdminSignupAuth.builder()
			.email(email)
			.authCode(authCode)
			.build();
		return adminSignupAuthRepository.save(adminSignupAuth);
	}

	@Transactional
	public void createAdminPasswordFindVerify(
		CreateAdminValidateSignupAuthRequest createAdminValidateSignupAuthRequest) {
		final String email = createAdminValidateSignupAuthRequest.getEmail();
		final Integer requestAuthCode = createAdminValidateSignupAuthRequest.getAuthCode();

		AdminSignupAuth adminSignupAuth = adminSignupAuthRepository.findById(email)
			.orElseThrow(() -> AdminInvalidAuthCodeException.EXCEPTION);

		if (!adminSignupAuth.getAuthCode().equals(requestAuthCode)) {
			throw AdminInvalidAuthCodeException.EXCEPTION;
		}
	}

	@Transactional
	public void createAdminPasswordFind(String email, Integer authCode) {
		AdminPasswordFindAuth adminPasswordFindAuth = AdminPasswordFindAuth.builder()
				.email(email)
				.authCode(authCode)
				.build();
		adminPasswordFindRepository.save(adminPasswordFindAuth);
	}

	@Transactional(readOnly = true)
	public void validateAdminPasswordFind(CreateAdminPasswordFindVerifyRequest createAdminPasswordFindVerifyRequest) {
		String email = createAdminPasswordFindVerifyRequest.getEmail();
		Integer requestAuthCode = createAdminPasswordFindVerifyRequest.getAuthCode();
		AdminPasswordFindAuth adminPasswordFindAuth = adminPasswordFindRepository.findById(email)
				.orElseThrow(() -> AdminInvalidAuthCodeException.EXCEPTION);

		if (!adminPasswordFindAuth.getAuthCode().equals(requestAuthCode)) {
			throw AdminInvalidAuthCodeException.EXCEPTION;
		}
	}
}
