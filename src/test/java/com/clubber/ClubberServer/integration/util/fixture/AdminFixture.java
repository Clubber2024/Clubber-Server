package com.clubber.ClubberServer.integration.util.fixture;

import com.clubber.ClubberServer.domain.admin.domain.AdminPasswordFindAuth;
import com.clubber.ClubberServer.domain.admin.domain.AdminSignupAuth;
import com.clubber.ClubberServer.domain.admin.dto.*;
import com.clubber.ClubberServer.domain.club.domain.ClubType;

import static com.clubber.ClubberServer.global.common.consts.ClubberStatic.IMAGE_SERVER;

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
		String contact,
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

	public static CreateAdminMailAuthRequest 인증_메일_전송_요청(String email){
		return new CreateAdminMailAuthRequest(email);
	}

	public static CreateAdminSignupAuthVerifyRequest 회원가입_이메일_인증_요청(String email, Integer authCode) {
		return new CreateAdminSignupAuthVerifyRequest(email, authCode);
	}

	public static AdminSignupAuth 회원가입_이메일_인증(String email, Integer authCode) {
		return AdminSignupAuth.builder()
				.email(email)
				.authCode(authCode)
				.build();
	}

	public static AdminPasswordFindAuth 비밀번호_찾기_인증(String email, Integer authCode){
		return AdminPasswordFindAuth.builder()
				.email(email)
				.authCode(authCode)
				.build();
	}

	public static CreateAdminPasswordFindAuthVerifyRequest 비밀번호찾기_인증번호_검증요청(String email, Integer authCode){
		return new CreateAdminPasswordFindAuthVerifyRequest(email, authCode);
	}

	public static UpdateAdminsPasswordRequest 관리자_비밀번호_변경_요청(String oldPassword, String newPassword){
		return new UpdateAdminsPasswordRequest(oldPassword, newPassword);
	}
}
