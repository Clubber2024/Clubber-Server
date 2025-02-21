package com.clubber.ClubberServer.domain.admin.service;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.global.infrastructure.outer.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final AdminReadService adminReadService;
	private final PasswordEncoder encoder;
	private final MailService mailService;

	public void sendAdminAuthEmail(String adminEmail, String authString) {
		final String subject = "[클러버] 동아리 관리자 계정 인증 번호입니다.";
		mailService.send(adminEmail, subject, authString);
	}

	@Transactional
	public Admin updateAdminAccount(String adminEmail, String username, String requestAuthString) {
		String encodedPassword = encoder.encode(requestAuthString);

		Admin admin = adminReadService.getAdminByEmail(adminEmail);
		admin.updatePassword(encodedPassword);
		admin.updateUsername(username);

		return admin;
	}
}
