package com.clubber.ClubberServer.integration.domain.admin.service;

import com.clubber.ClubberServer.domain.admin.domain.AdminPasswordFindAuth;
import com.clubber.ClubberServer.domain.admin.domain.AdminSignupAuth;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminPasswordFindAuthVerifyRequest;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminSignupAuthVerifyRequest;
import com.clubber.ClubberServer.domain.admin.repository.AdminPasswordFindAuthRepository;
import com.clubber.ClubberServer.domain.admin.repository.AdminSignupAuthRepository;
import com.clubber.ClubberServer.domain.admin.service.AdminEmailAuthService;
import com.clubber.ClubberServer.integration.util.ServiceTest;
import com.clubber.ClubberServer.integration.util.fixture.AdminFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminEmailAuthServiceTest extends ServiceTest {
    @Autowired
    private AdminPasswordFindAuthRepository adminPasswordFindAuthRepository;

    @Autowired
    private AdminSignupAuthRepository adminSignupAuthRepository;

    @Autowired
    private AdminEmailAuthService adminEmailAuthService;

    @DisplayName("관리자 비밀번호 찾기 인증번호 검증을 수행한다")
    @Test
    void validateAdminPasswordFindAuth() {
        //given
        final String email = "test@gmail.com";
        final Integer authCode = 123456;
        AdminPasswordFindAuth adminPasswordFindAuth = AdminFixture.비밀번호_찾기_인증(email, authCode);
        adminPasswordFindAuthRepository.save(adminPasswordFindAuth);
        CreateAdminPasswordFindAuthVerifyRequest request = AdminFixture.비밀번호찾기_인증번호_검증요청(email, authCode);

        //when & then
        Assertions.assertThatCode(() -> adminEmailAuthService.createAdminPasswordFindAuthVerify(request))
                .doesNotThrowAnyException();
    }

    @DisplayName("관리자 회원가입 인증번호 검증을 수행한다")
    @Test
    void validateAdminSignupAuthVerify() {
        //given
        final String email = "test@gmail.com";
        final Integer authCode = 123456;
        AdminSignupAuth adminSignupAuth = AdminFixture.회원가입_이메일_인증(email, authCode);
        adminSignupAuthRepository.save(adminSignupAuth);
        CreateAdminSignupAuthVerifyRequest request = AdminFixture.회원가입_이메일_인증_요청(email, authCode);

        //when & then
        Assertions.assertThatCode(() -> adminEmailAuthService.createAdminSignupAuthVerify(request))
                .doesNotThrowAnyException();
    }
}
