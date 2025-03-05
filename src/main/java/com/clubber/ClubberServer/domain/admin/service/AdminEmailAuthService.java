package com.clubber.ClubberServer.domain.admin.service;

import com.clubber.ClubberServer.domain.admin.domain.AdminPasswordFindAuth;
import com.clubber.ClubberServer.domain.admin.domain.AdminSignupAuth;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminPasswordFindVerifyRequest;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminSignupAuthVerifyRequest;
import com.clubber.ClubberServer.domain.admin.exception.AdminInvalidAuthCodeException;
import com.clubber.ClubberServer.domain.admin.repository.AdminPasswordFindAuthRepository;
import com.clubber.ClubberServer.domain.admin.repository.AdminSignupAuthRepository;
import com.clubber.ClubberServer.domain.admin.validator.AdminValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminEmailAuthService {

	private final AdminSignupAuthRepository adminSignupAuthRepository;
	private final AdminPasswordFindAuthRepository adminPasswordFindAuthRepository;
	private final AdminValidator adminValidator;

	@Transactional
	public AdminSignupAuth createAdminSignupAuth(String email, Integer authCode) {
		AdminSignupAuth adminSignupAuth = AdminSignupAuth.builder()
			.email(email)
			.authCode(authCode)
			.build();
		return adminSignupAuthRepository.save(adminSignupAuth);
	}

	@Transactional(readOnly = true)
	public void createAdminSignupAuthVerify(
		CreateAdminSignupAuthVerifyRequest createAdminVerifySignupAuthRequest) {
		final String email = createAdminVerifySignupAuthRequest.getEmail();
		final Integer requestAuthCode = createAdminVerifySignupAuthRequest.getAuthCode();

		AdminSignupAuth adminSignupAuth = adminSignupAuthRepository.findById(email)
			.orElseThrow(() -> AdminInvalidAuthCodeException.EXCEPTION);

		if (!adminSignupAuth.getAuthCode().equals(requestAuthCode)) {
			throw AdminInvalidAuthCodeException.EXCEPTION;
		}
	}

	@Transactional
	public void createAdminPasswordFindAuth(String email, Integer authCode) {
		AdminPasswordFindAuth adminPasswordFindAuth = AdminPasswordFindAuth.builder()
				.email(email)
				.authCode(authCode)
				.build();
		adminPasswordFindAuthRepository.save(adminPasswordFindAuth);
	}

	@Transactional(readOnly = true)
	public void createAdminPasswordFindVerify(CreateAdminPasswordFindVerifyRequest createAdminPasswordFindVerifyRequest) {
		String email = createAdminPasswordFindVerifyRequest.getEmail();
		Integer requestAuthCode = createAdminPasswordFindVerifyRequest.getAuthCode();
		AdminPasswordFindAuth adminPasswordFindAuth = adminPasswordFindAuthRepository.findById(email)
				.orElseThrow(() -> AdminInvalidAuthCodeException.EXCEPTION);

		if (!adminPasswordFindAuth.getAuthCode().equals(requestAuthCode)) {
			throw AdminInvalidAuthCodeException.EXCEPTION;
		}
	}
}
