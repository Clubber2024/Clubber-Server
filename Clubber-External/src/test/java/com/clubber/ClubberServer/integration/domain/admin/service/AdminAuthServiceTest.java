package com.clubber.ClubberServer.integration.domain.admin.service;

import com.clubber.domain.domains.admin.domain.Admin;
import com.clubber.domain.admin.dto.CreateAdminsLoginRequest;
import com.clubber.domain.admin.repository.AdminRepository;
import com.clubber.domain.admin.service.AdminAuthService;
import com.clubber.ClubberServer.integration.util.fixture.AdminFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@ActiveProfiles("test")
public class AdminAuthServiceTest {

    @Autowired
    private AdminAuthService adminAuthService;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Test
    void 관리자_로그인_성공() {
        //given
        final String username = "clubber123";
        final String password = "password";
        Admin admin = AdminFixture.aAdmin()
                .username(username)
                .password(encoder.encode(password))
                .build();
        adminRepository.save(admin);

        CreateAdminsLoginRequest request = AdminFixture.a_관리자_로그인_요청()
                .set("username", username)
                .set("password", password)
                .sample();

        //when & then
        assertThatCode(() -> adminAuthService.createAdminsLogin(request))
                .doesNotThrowAnyException();
    }
}
