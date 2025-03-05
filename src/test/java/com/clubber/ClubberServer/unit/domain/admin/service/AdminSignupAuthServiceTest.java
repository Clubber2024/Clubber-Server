package com.clubber.ClubberServer.unit.domain.admin.service;

import com.clubber.ClubberServer.domain.admin.domain.AdminSignupAuth;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminSignupAuthVerifyRequest;
import com.clubber.ClubberServer.domain.admin.repository.AdminSignupAuthRepository;
import com.clubber.ClubberServer.domain.admin.service.AdminEmailAuthService;
import com.clubber.ClubberServer.integration.util.ServiceTest;
import com.clubber.ClubberServer.integration.util.fixture.AdminFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminSignupAuthServiceTest extends ServiceTest {

	@Autowired
	private AdminEmailAuthService adminEmailAuthService;

	@Autowired
	private AdminSignupAuthRepository adminSignupAuthRepository;

	@Test
	@DisplayName("회원가입 이메일 인증 수행")
	void validateAdminEmailAuthTest() {
		//given
		final String email = "ssuclubber@gmail.com";
		final Integer authCode = 123456;
		AdminSignupAuth adminSignupAuth = AdminFixture.회원가입_이메일_인증(email, authCode);
		adminSignupAuthRepository.save(adminSignupAuth);
		CreateAdminSignupAuthVerifyRequest 회원가입_이메일_인증_요청 = AdminFixture.회원가입_이메일_인증_요청(email, authCode);

		//when & then

		//tear down
		adminSignupAuthRepository.delete(adminSignupAuth);
	}
}
