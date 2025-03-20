package com.clubber.ClubberServer.integration.util.fixture;

import com.clubber.ClubberServer.domain.admin.domain.AdminPasswordFindAuth;
import com.clubber.ClubberServer.domain.admin.domain.AdminSignupAuth;
import com.clubber.ClubberServer.domain.admin.domain.AdminUsernameFindAuth;

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

    public static AdminPasswordFindAuth.AdminPasswordFindAuthBuilder aAdminPasswordFindAuth() {
        return AdminPasswordFindAuth.builder()
                .username(USERNAME)
                .authCode(AUTH_CODE);
    }

    public static AdminUsernameFindAuth.AdminUsernameFindAuthBuilder aAdminUsernameFindAuth() {
        return AdminUsernameFindAuth.builder()
                .clubId(CLUB_ID)
                .authCode(AUTH_CODE);
    }
}
