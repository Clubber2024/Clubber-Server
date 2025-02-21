package com.clubber.ClubberServer.domain.admin.service;

import com.clubber.ClubberServer.global.infrastructure.outer.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}
