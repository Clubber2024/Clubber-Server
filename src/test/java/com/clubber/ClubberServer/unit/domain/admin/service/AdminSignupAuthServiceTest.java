package com.clubber.ClubberServer.unit.domain.admin.service;

import static org.assertj.core.api.Assertions.*;

import com.clubber.ClubberServer.domain.admin.domain.AdminSignupAuth;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminVerifyEmailAuthRequest;
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
	@DisplayName("이메일과 인증번호가 같다면 인증 상태가 true로 변경된다")
	void validateAdminEmailAuthTest() {
		//given
		final String email = "ssuclubber@gmail.com";
		final String authCode = "authcode";
		AdminSignupAuth adminSignupAuth = AdminFixture.이메일_인증(email, authCode);
		AdminSignupAuth savedAdminSignupAuth = adminSignupAuthRepository.save(adminSignupAuth);
		UpdateAdminVerifyEmailAuthRequest request = AdminFixture.이메일_인증_요청(savedAdminSignupAuth.getId(), email, authCode);

		//when
		adminEmailAuthService.createAdminPasswordFindVerify(request);
		AdminSignupAuth verifiedAdminSignupAuth = adminSignupAuthRepository.findById(savedAdminSignupAuth.getId()).get();

		//then
		assertThat(verifiedAdminSignupAuth.isEmailVerified()).isEqualTo(true);
	}
}
