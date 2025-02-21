package com.clubber.ClubberServer.domain.admin.service;

import com.clubber.ClubberServer.domain.admin.domain.AdminEmailAuth;
import com.clubber.ClubberServer.domain.admin.exception.AdminInvalidAuthStringException;
import com.clubber.ClubberServer.domain.admin.repository.AdminEmailAuthRepository;
import com.clubber.ClubberServer.domain.admin.validator.AdminValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminEmailAuthService {

	private final AdminEmailAuthRepository adminEmailAuthRepository;
	private final AdminValidator adminValidator;

	public void deleteAdminEmailAuth(AdminEmailAuth adminEmailAuth) {
		adminEmailAuthRepository.delete(adminEmailAuth);
	}

	public void createAdminMailAuth(String adminEmail, String authString) {
		AdminEmailAuth adminEmailAuth = AdminEmailAuth.builder()
			.email(adminEmail)
			.authRandomString(authString)
			.build();
		adminEmailAuthRepository.save(adminEmailAuth);
	}

	public AdminEmailAuth validateAdminEmailAuth(String adminEmail, String requestAuthString) {
		AdminEmailAuth adminEmailAuth = getAdminAuthByEmailAndAuthString(adminEmail,
			requestAuthString);
		String savedAuthString = adminEmailAuth.getAuthRandomString();
		adminValidator.validateAuthString(requestAuthString, savedAuthString);
		return adminEmailAuth;
	}

	@Transactional(readOnly = true)
	public AdminEmailAuth getAdminAuthByEmailAndAuthString(String adminEmail, String authString) {
		return adminEmailAuthRepository.findByEmailAndAuthRandomString(adminEmail, authString)
			.orElseThrow(() -> AdminInvalidAuthStringException.EXCEPTION);
	}
}
