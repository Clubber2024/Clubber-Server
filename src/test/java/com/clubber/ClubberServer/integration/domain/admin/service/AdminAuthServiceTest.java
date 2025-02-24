package com.clubber.ClubberServer.integration.domain.admin.service;

import static com.clubber.ClubberServer.domain.user.domain.AccountState.ACTIVE;
import static com.clubber.ClubberServer.integration.util.fixture.AdminFixture.VALID_ADMIN_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminsLoginResponse;
import com.clubber.ClubberServer.domain.admin.repository.AdminRepository;
import com.clubber.ClubberServer.domain.admin.service.AdminAuthService;
import com.clubber.ClubberServer.integration.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminAuthServiceTest extends ServiceTest {

	@Autowired
	private AdminAuthService adminAuthService;

	@Autowired
	private AdminRepository adminRepository;

	@DisplayName("관리자 로그인을 수행한다")
	@Test
	void adminLogin() {
		CreateAdminsLoginResponse createAdminLoginReponse = adminAuthService.createAdminsLogin(
			VALID_ADMIN_REQUEST);
		Admin admin = adminRepository.findAdminByIdAndAccountState(
			createAdminLoginReponse.getAdminId(), ACTIVE).get();

		assertAll(
			() -> assertThat(admin).isNotNull(),
			() -> assertThat(admin.getUsername()).isEqualTo(VALID_ADMIN_REQUEST.getUsername())
		);
	}
}
