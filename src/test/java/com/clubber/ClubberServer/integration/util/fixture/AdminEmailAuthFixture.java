package com.clubber.ClubberServer.integration.util.fixture;

import com.clubber.ClubberServer.domain.admin.domain.AdminPasswordFindAuth;
import com.clubber.ClubberServer.domain.admin.domain.AdminSignupAuth;
import com.clubber.ClubberServer.domain.admin.domain.AdminUsernameFindAuth;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminSignupAuthVerifyRequest;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminPasswordFindAuthVerifyRequest;

public class AdminEmailAuthFixture {
    public static Long CLUB_ID = 1L;
    public static String CLUB_NAME = "클러버";
    public static String USERNAME = "clubber123";
    public static String EMAIL = "ssuclubber@gmail.com";
    public static Integer AUTH_CODE = 123456;


    public static AdminSignupAuth.AdminSignupAuthBuilder aAdminSignupAuth() {
        return AdminSignupAuth.builder()
                .clubName(CLUB_NAME)
                .email(EMAIL)
                .authCode(AUTH_CODE);
    }

    public static CreateAdminSignupAuthVerifyRequest 회원가입_이메일_인증_요청(String clubName, String email, Integer authCode) {
        return new CreateAdminSignupAuthVerifyRequest(clubName, email, authCode);
    }

    public static AdminPasswordFindAuth.AdminPasswordFindAuthBuilder aAdminPasswordFindAuth() {
        return AdminPasswordFindAuth.builder()
                .username(USERNAME)
                .authCode(AUTH_CODE);
    }

    public static UpdateAdminPasswordFindAuthVerifyRequest 비밀번호_찾기_인증_요청(String username, Integer authCode){
        return new UpdateAdminPasswordFindAuthVerifyRequest(username, authCode);
    }

    public static AdminUsernameFindAuth.AdminUsernameFindAuthBuilder aAdminUsernameFindAuth() {
        return AdminUsernameFindAuth.builder()
                .clubId(CLUB_ID)
                .authCode(AUTH_CODE);
    }
}
