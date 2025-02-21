package com.clubber.ClubberServer.domain.admin.service;

import com.clubber.ClubberServer.domain.admin.domain.AdminEmailAuth;
import com.clubber.ClubberServer.domain.admin.exception.AdminInvalidAuthStringException;
import com.clubber.ClubberServer.domain.admin.repository.AdminEmailAuthRepository;
import com.clubber.ClubberServer.domain.admin.validator.AdminValidator;
import com.clubber.ClubberServer.global.infrastructure.outer.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminEmailAuthService {

	private final AdminEmailAuthRepository adminEmailAuthRepository;
	private final AdminValidator adminValidator;
	private final MailService mailService;

	@Transactional
	public void deleteAdminEmailAuth(AdminEmailAuth adminEmailAuth) {
		adminEmailAuthRepository.delete(adminEmailAuth);
	}

	@Transactional
	public void createAdminMailAuth(String adminEmail, String authString) {
		AdminEmailAuth adminEmailAuth = AdminEmailAuth.builder()
			.email(adminEmail)
			.authRandomString(authString)
			.build();
		adminEmailAuthRepository.save(adminEmailAuth);
	}

	@Transactional(readOnly = true)
	public AdminEmailAuth validateAdminEmailAuth(String adminEmail, String requestAuthString) {
		AdminEmailAuth adminEmailAuth = adminEmailAuthRepository.findByEmailAndAuthRandomString(
				adminEmail, requestAuthString)
			.orElseThrow(() -> AdminInvalidAuthStringException.EXCEPTION);

		String savedAuthString = adminEmailAuth.getAuthRandomString();
		adminValidator.validateAuthString(requestAuthString, savedAuthString);
		return adminEmailAuth;
	}

	public void sendAdminAuthEmail(String adminEmail, String authString) {
		final String subject = "[클러버] 동아리 관리자 계정 인증 번호입니다.";
		mailService.send(adminEmail, subject, authString);
	}
}
