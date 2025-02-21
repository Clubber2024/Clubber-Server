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
	public void createAdminMailAuth(String email, String authCode) {
		AdminEmailAuth adminEmailAuth = AdminEmailAuth.builder()
			.email(email)
			.authCode(authCode)
			.build();
		adminEmailAuthRepository.save(adminEmailAuth);
	}

	@Transactional(readOnly = true)
	public AdminEmailAuth validateAdminEmailAuth(String email, String authCode) {
		AdminEmailAuth adminEmailAuth = adminEmailAuthRepository.findByEmailAndAuthCode(
				email, authCode)
			.orElseThrow(() -> AdminInvalidAuthStringException.EXCEPTION);

		String storedAuthCode = adminEmailAuth.getAuthCode();
		adminValidator.validateAuthString(authCode, storedAuthCode);
		return adminEmailAuth;
	}

	public void sendAdminAuthEmail(String email, String authCode) {
		final String subject = "[클러버] 동아리 관리자 계정 인증 번호입니다.";
		mailService.send(email, subject, authCode);
	}
}
