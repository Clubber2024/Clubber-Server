package com.clubber.ClubberServer.integration.util.fixture;

import com.clubber.domain.domains.admin.domain.Admin;
import com.clubber.domain.domains.admin.domain.AdminSignupAuth;
import com.clubber.domain.domains.admin.domain.Contact;
import com.clubber.domain.domains.admin.domain.PendingAdminInfo;
import com.clubber.domain.admin.dto.*;
import com.clubber.domain.domains.club.domain.ClubType;
import com.clubber.domain.domains.club.domain.College;
import com.clubber.domain.domains.club.domain.Department;
import com.navercorp.fixturemonkey.ArbitraryBuilder;

import static com.clubber.domain.domains.user.domain.AccountRole.ADMIN;
import static com.clubber.domain.domains.user.domain.AccountState.ACTIVE;
import static com.clubber.common.consts.ClubberStatic.IMAGE_SERVER;
import static com.clubber.ClubberServer.integration.util.fixture.FixtureCommon.fixtureMonkey;

public class AdminFixture {

    public static final String USERNAME = "clubber123";
    public static final String EMAIL = "ssuclubber@gmail.com";
    public static final String OLD_PASSWORD = "oldPassword";
    public static final String NEW_PASSWORD = "newPassword";
    public static final String INSTAGRAM = "@clubber_ssu";
    public static final String NAME = "clubber";
    public static final Integer AUTH_CODE = 123456;

    public static final UpdateClubPageRequest VALID_UPDATE_CLUB_PAGE_REQUEST =
            new UpdateClubPageRequest("수정imagekey", "수정introduction", "수정instagram", "수정youtube", "수정activity",
                    "수정leader", 1000L);

    public static final UpdateClubPageRequest IMAGE_KEY_WITH_IMAGE_SERVER_PAGE_REQUEST =
            new UpdateClubPageRequest(IMAGE_SERVER + "수정imagekey", "수정introduction", "수정instagram", "수정youtube",
                    "수정activity", "수정leader", 1000L);

    public static final UpdateClubPageRequest OVER_MAX_LENGTH_ACTIVITY_UPDATE_PAGE_REQUEST =
            new UpdateClubPageRequest("imagekey", "introduction", "instagram", "youtube", "a".repeat(1501),
                    "leader", 1000L);

    public static final UpdateClubPageRequest OVER_MAX_LENGTH_INTRODUCTION_UPDATE_PAGE_REQUEST =
            new UpdateClubPageRequest("imagekey", "a".repeat(101), "instagram", "youtube", "activity", "leader",
                    1000L);


    public static Admin.AdminBuilder aAdmin() {
        return Admin.builder()
                .username(USERNAME)
                .email(EMAIL)
                .password(OLD_PASSWORD)
                .accountState(ACTIVE)
                .accountRole(ADMIN)
                .contact(
                        new Contact(INSTAGRAM, null)
                );
    }

    public static AdminSignupAuth.AdminSignupAuthBuilder aAdminSignupAuth() {
        return AdminSignupAuth.builder()
                .clubName(NAME)
                .authCode(AUTH_CODE)
                .email(EMAIL);
    }

    public static PendingAdminInfo.PendingAdminInfoBuilder aPendingAdminInfo() {
        return PendingAdminInfo.builder()
                .username(USERNAME)
                .password(OLD_PASSWORD)
                .clubType(ClubType.SMALL)
                .college(College.IT_COLLEGE)
                .department(Department.COMPUTER_SCIENCE)
                .clubName(ClubFixture.CLUB_NAME)
                .email(EMAIL)
                .contact(
                        new Contact(INSTAGRAM, null)
                );
    }

    public static ArbitraryBuilder<CreateAdminsLoginRequest> a_관리자_로그인_요청() {
        return fixtureMonkey.giveMeBuilder(CreateAdminsLoginRequest.class)
                .set("username", USERNAME)
                .set("password", OLD_PASSWORD);
    }

    public static ArbitraryBuilder<UpdateAdminsPasswordRequest> a_마이페이지_비밀번호_변경_요청() {
        return fixtureMonkey.giveMeBuilder(UpdateAdminsPasswordRequest.class)
                .set("oldPassword", OLD_PASSWORD)
                .set("newPassword", NEW_PASSWORD);
    }

    public static ArbitraryBuilder<CreateAdminPasswordFindRequest> a_비밀번호_찾기_요청() {
        return fixtureMonkey.giveMeBuilder(CreateAdminPasswordFindRequest.class)
                .set("username", USERNAME)
                .set("email", EMAIL);
    }

    public static ArbitraryBuilder<UpdateAdminContactRequest> a_연락수단_변경_요청() {
        return fixtureMonkey.giveMeBuilder(UpdateAdminContactRequest.class);
    }

    public static ArbitraryBuilder<CreateAdminSignUpRequest> a_회원가입_요청() {
        return fixtureMonkey.giveMeBuilder(CreateAdminSignUpRequest.class)
                .set("contact", new Contact(INSTAGRAM, null));
    }
}
