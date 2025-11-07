package com.clubber.ClubberServer.integration.domain.admin.service;

import com.clubber.domain.domains.admin.domain.AdminPasswordFindAuth;
import com.clubber.domain.domains.admin.domain.AdminSignupAuth;
import com.clubber.domain.domains.admin.domain.AdminUsernameFindAuth;
import com.clubber.domain.admin.dto.CreateAdminSignupAuthVerifyRequest;
import com.clubber.domain.admin.dto.GetAdminUsernameFindRequest;
import com.clubber.domain.admin.dto.UpdateAdminPasswordFindAuthVerifyRequest;
import com.clubber.domain.domains.admin.repository.AdminPasswordFindAuthRepository;
import com.clubber.domain.domains.admin.repository.AdminSignupAuthRepository;
import com.clubber.domain.domains.admin.repository.AdminUsernameFindAuthRepository;
import com.clubber.domain.admin.service.AdminEmailAuthService;
import com.clubber.ClubberServer.integration.util.fixture.AdminEmailAuthFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@ActiveProfiles("test")
public class AdminEmailAuthServiceTest {

    @Autowired
    private AdminSignupAuthRepository adminSignupAuthRepository;

    @Autowired
    private AdminUsernameFindAuthRepository adminUsernameFindAuthRepository;

    @Autowired
    private AdminPasswordFindAuthRepository adminPasswordFindAuthRepository;

    @Autowired
    private AdminEmailAuthService adminEmailAuthService;

    @Test
    void 동아리_관리자_비밀번호_찾기_인증번호_검증() {
        //given
        final String username = "clubber";
        final Integer authCode = 123456;

        AdminPasswordFindAuth adminPasswordFindAuth = AdminEmailAuthFixture.aAdminPasswordFindAuth()
                .username(username)
                .authCode(authCode)
                .build();
        adminPasswordFindAuthRepository.save(adminPasswordFindAuth);

        UpdateAdminPasswordFindAuthVerifyRequest request = AdminEmailAuthFixture.비밀번호_찾기_인증_요청(username, authCode);

        //when & then
        Assertions.assertThatCode(() -> adminEmailAuthService.updateAdminPasswordFindAuthVerify(request))
                .doesNotThrowAnyException();
    }

    @Test
    void 동아리_아이디_찾기_인증번호_검증() {
        //given
        final Long clubId = 1L;
        final Integer authCode = 123456;
        final String email = "ssuclubber@gmail.com";

        AdminUsernameFindAuth adminUsernameFindAuth = AdminEmailAuthFixture.aAdminUsernameFindAuth()
                .clubId(clubId)
                .authCode(authCode)
                .build();
        adminUsernameFindAuthRepository.save(adminUsernameFindAuth);

        GetAdminUsernameFindRequest request = AdminEmailAuthFixture.아이디_찾기_인증_요청(clubId, email, authCode);

        //when & then
        Assertions.assertThatCode(() -> adminEmailAuthService.createAdminUsernameFindAuth(request.getClubId(), request.getAuthCode()))
                .doesNotThrowAnyException();
    }

    @Test
    void 동아리_관리자_회원가입_인증번호_검증() {
        //given
        final String clubName = "clubber";
        final String email = "ssuclubber@gmail.com";
        final Integer authCode = 123456;

        AdminSignupAuth adminSignupAuth = AdminEmailAuthFixture.aAdminSignupAuth()
                .clubName(clubName)
                .email(email)
                .authCode(authCode)
                .build();
        adminSignupAuthRepository.save(adminSignupAuth);

        CreateAdminSignupAuthVerifyRequest request = AdminEmailAuthFixture.회원가입_이메일_인증_요청(clubName, email, authCode);

        //when & then
        Assertions.assertThatCode(() -> adminEmailAuthService.updateVerifyAdminSignupAuth(request))
                .doesNotThrowAnyException();
    }
}
