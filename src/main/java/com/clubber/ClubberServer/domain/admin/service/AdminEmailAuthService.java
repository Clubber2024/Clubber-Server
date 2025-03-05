package com.clubber.ClubberServer.domain.admin.service;

import com.clubber.ClubberServer.domain.admin.domain.AdminPasswordFindAuth;
import com.clubber.ClubberServer.domain.admin.domain.AdminSignupAuth;
import com.clubber.ClubberServer.domain.admin.dto.GetAdimPasswordFindValidateRequest;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminAuthResponse;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminSignupAuthRequest;
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
	public AdminSignupAuth createAdminMailAuth(String email, Integer authCode) {
		AdminSignupAuth adminSignupAuth = AdminSignupAuth.builder()
			.email(email)
			.authCode(authCode)
			.build();
		return adminSignupAuthRepository.save(adminSignupAuth);
	}

	@Transactional
	public void validateAdminEmailAuth(
		CreateAdminSignupAuthRequest createAdminSignupAuthRequest) {
		final String email = createAdminSignupAuthRequest.getEmail();
		final Integer requestAuthCode = createAdminSignupAuthRequest.getAuthCode();

		AdminSignupAuth adminSignupAuth = adminSignupAuthRepository.findById(email)
			.orElseThrow(() -> AdminInvalidAuthCodeException.EXCEPTION);

		if (!adminSignupAuth.getAuthCode().equals(requestAuthCode)) {
			throw AdminInvalidAuthCodeException.EXCEPTION;
		}
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
