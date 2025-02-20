package com.clubber.ClubberServer.domain.admin.service;

import com.clubber.ClubberServer.domain.admin.domain.AdminEmailAuth;
import com.clubber.ClubberServer.domain.admin.exception.AdminInvalidAuthStringException;
import com.clubber.ClubberServer.domain.admin.repository.AdminEmailAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminAuthService {

	private AdminEmailAuthRepository adminEmailAuthRepository;

	@Transactional(readOnly = true)
	public AdminEmailAuth getAdminAuthByEmailAndAuthString(String adminEmail, String authString) {
		return adminEmailAuthRepository.findByEmailAndAuthRandomString(adminEmail, authString)
			.orElseThrow(() -> AdminInvalidAuthStringException.EXCEPTION);
	}

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
}
