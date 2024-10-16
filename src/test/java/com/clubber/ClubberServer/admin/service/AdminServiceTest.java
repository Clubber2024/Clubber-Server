package com.clubber.ClubberServer.admin.service;

import static com.clubber.ClubberServer.util.fixture.AdminFixture.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminsLoginResponse;
import com.clubber.ClubberServer.domain.admin.repository.AdminRepository;
import com.clubber.ClubberServer.domain.admin.service.AdminService;
import com.clubber.ClubberServer.util.ServiceTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.assertj.core.api.Assertions.assertThat;

public class AdminServiceTest extends ServiceTest {

	@Autowired
	private AdminService adminService;

	@Autowired
	private AdminRepository adminRepository;

	@DisplayName("관리자 로그인을 수행한다")
	@Test
	void adminLogin(){
		CreateAdminsLoginResponse createAdminLoginReponse = adminService.createAdminsLogin(VALID_ADMIN_REQUEST);
		Optional<Admin> savedAdmin = adminRepository.findById(createAdminLoginReponse.getAdminId());

		assertAll(
			() -> assertThat(savedAdmin).isNotNull(),
			() -> assertThat(savedAdmin.get().getUsername()).isEqualTo(VALID_ADMIN_REQUEST.getUsername())
		);
	}
}
