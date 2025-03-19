package com.clubber.ClubberServer.integration.util.fixture;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.domain.AdminPasswordFindAuth;
import com.clubber.ClubberServer.domain.admin.domain.AdminSignupAuth;
import com.clubber.ClubberServer.domain.admin.domain.Contact;
import com.clubber.ClubberServer.domain.admin.dto.*;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.domain.ClubInfo;
import com.clubber.ClubberServer.domain.club.domain.ClubType;
import com.navercorp.fixturemonkey.ArbitraryBuilder;
import com.navercorp.fixturemonkey.FixtureMonkey;

import static com.clubber.ClubberServer.domain.user.domain.AccountRole.ADMIN;
import static com.clubber.ClubberServer.domain.user.domain.AccountState.ACTIVE;
import static com.clubber.ClubberServer.global.common.consts.ClubberStatic.IMAGE_SERVER;
import static com.clubber.ClubberServer.integration.util.fixture.FixtureCommon.*;

public class AdminFixture {

	public static final CreateAdminsLoginRequest VALID_ADMIN_REQUEST = new CreateAdminsLoginRequest(
		"동아리 1", "비밀번호 1");

	public static final UpdateAdminsPasswordRequest VALID_UPDATE_PASSWORD_REQUEST = new UpdateAdminsPasswordRequest("기존비밀번호",
		"수정비밀번호");

	public static final UpdateClubPageRequest VALID_UPDATE_CLUB_PAGE_REQUEST =
		new UpdateClubPageRequest("수정imagekey", "수정introduction", "수정instagram", "수정youtube","수정activity",
			"수정leader", 1000L);

	public static final UpdateClubPageRequest IMAGE_KEY_WITH_IMAGE_SERVER_PAGE_REQUEST =
		new UpdateClubPageRequest(IMAGE_SERVER + "수정imagekey", "수정introduction", "수정instagram","수정youtube",
			"수정activity", "수정leader", 1000L);

	public static final UpdateClubPageRequest OVER_MAX_LENGTH_ACTIVITY_UPDATE_PAGE_REQUEST =
		new UpdateClubPageRequest("imagekey", "introduction", "instagram","youtube", "a".repeat(1501),
			"leader", 1000L);

	public static final UpdateClubPageRequest OVER_MAX_LENGTH_INTRODUCTION_UPDATE_PAGE_REQUEST =
		new UpdateClubPageRequest("imagekey", "a".repeat(101), "instagram", "youtube", "activity", "leader",
			1000L);


	public static CreateAdminSignUpRequest 회원가입_요청(
		String username,
		String password,
		ClubType clubType,
		String clubName,
		String email,
		Contact contact,
		String imageForApproval) {

		return new CreateAdminSignUpRequest(
			username,
			password,
			clubType,
			clubName,
			email,
			contact,
			imageForApproval
		);
	}

	public static CreateAdminPasswordFindRequest 인증_메일_전송_요청(String username, String email){
		return new CreateAdminPasswordFindRequest(username, email);
	}

	public static CreateAdminSignupAuthVerifyRequest 회원가입_이메일_인증_요청(String clubName, String email, Integer authCode) {
		return new CreateAdminSignupAuthVerifyRequest(clubName, email, authCode);
	}

	public static AdminSignupAuth 회원가입_이메일_인증(String clubName, String email, Integer authCode) {
		return AdminSignupAuth.builder()
				.clubName(clubName)
				.email(email)
				.authCode(authCode)
				.build();
	}

	public static AdminPasswordFindAuth 비밀번호_찾기_인증(String email, Integer authCode){
		return AdminPasswordFindAuth.builder()
				.authCode(authCode)
				.build();
	}

	public static UpdateAdminPasswordFindAuthVerifyRequest 비밀번호찾기_인증번호_검증요청(String email, Integer authCode){
		return new UpdateAdminPasswordFindAuthVerifyRequest(email, authCode);
	}

	public static Admin.AdminBuilder getDefaultAdminBuilder(){
		return Admin.builder()
				.id(1L)
				.username("clubber")
				.email("ssuclubber@gmail.com")
				.password("password")
				.accountState(ACTIVE)
				.accountRole(ADMIN)
				.contact(
						new Contact("@clubber_ssu", null)
				);
	}

	public static UpdateAdminsPasswordRequest getUpdateAdminsPasswordRequest(String oldPassword, String newPassword) {
		return fixtureMonkey.giveMeBuilder(UpdateAdminsPasswordRequest.class)
				.set("oldPassword", oldPassword)
				.set("newPassword", newPassword)
				.sample();
	}

	public static ArbitraryBuilder<CreateAdminPasswordFindRequest> getDefaultCreateAdminPasswordFindRequest(){
		return fixtureMonkey.giveMeBuilder(CreateAdminPasswordFindRequest.class)
				.set("username", "clubber")
				.set("email", "ssuclubber@gmail.com");
	}
}
