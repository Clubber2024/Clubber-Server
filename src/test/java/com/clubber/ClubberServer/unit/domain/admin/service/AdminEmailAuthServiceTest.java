package com.clubber.ClubberServer.unit.domain.admin.service;

import static org.assertj.core.api.Assertions.*;

import com.clubber.ClubberServer.domain.admin.domain.AdminEmailAuth;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminVerifyEmailAuthRequest;
import com.clubber.ClubberServer.domain.admin.repository.AdminEmailAuthRepository;
import com.clubber.ClubberServer.domain.admin.service.AdminEmailAuthService;
import com.clubber.ClubberServer.integration.util.ServiceTest;
import com.clubber.ClubberServer.integration.util.fixture.AdminFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminEmailAuthServiceTest extends ServiceTest {

	@Autowired
	private AdminEmailAuthService adminEmailAuthService;

	@Autowired
	private AdminEmailAuthRepository adminEmailAuthRepository;

	@Test
	@DisplayName("이메일과 인증번호가 같다면 인증 상태가 변경된다")
	void validateAdminEmailAuthTest() {
		//given
		final String email = "ssuclubber@gmail.com";
		final String authCode = "authcode";
		AdminEmailAuth adminEmailAuth = AdminFixture.이메일_인증(email, authCode);
		adminEmailAuthRepository.save(adminEmailAuth);
		UpdateAdminVerifyEmailAuthRequest request = AdminFixture.이메일_인증_요청(email, authCode);

		//when
		adminEmailAuthService.validateAdminEmailAuth(request);
		AdminEmailAuth verifiedAdminEmailAuth = adminEmailAuthRepository.findByEmailAndAuthCode(email,
			authCode).get();

		//then
		assertThat(verifiedAdminEmailAuth.isEmailVerified()).isEqualTo(true);
	}
}
