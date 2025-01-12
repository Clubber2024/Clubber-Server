package com.clubber.ClubberServer.util.fixture;

import static com.clubber.ClubberServer.global.common.ClubberStatic.*;

import com.clubber.ClubberServer.domain.admin.dto.CreateAdminsLoginRequest;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsPasswordRequest;
import com.clubber.ClubberServer.domain.admin.dto.UpdateClubPageRequest;

public class AdminFixture {
	public static final CreateAdminsLoginRequest VALID_ADMIN_REQUEST = new CreateAdminsLoginRequest("동아리 1","비밀번호 1");

	public static final UpdateAdminsPasswordRequest VALID_UPDATE_PASSWORD_REQUEST = new UpdateAdminsPasswordRequest("수정비밀번호");

	public static final UpdateClubPageRequest VALID_UPDATE_CLUB_PAGE_REQUEST =
		new UpdateClubPageRequest("수정imagekey", "수정introduction", "수정instagram", "수정activity", "수정leader", 1000L);

	public static final UpdateClubPageRequest IMAGE_KEY_WITH_IMAGE_SERVER_PAGE_REQUEST =
		new UpdateClubPageRequest(IMAGE_SERVER + "수정imagekey","수정introduction", "수정instagram", "수정activity", "수정leader", 1000L);

	public static final UpdateClubPageRequest OVER_MAX_LENGTH_ACTIVITY_UPDATE_PAGE_REQUEST =
			new UpdateClubPageRequest("imagekey", "introduction", "instagram", "a".repeat(1501), "leader", 1000L);

	public static final UpdateClubPageRequest OVER_MAX_LENGTH_INTRODUCTION_UPDATE_PAGE_REQUEST =
			new UpdateClubPageRequest("imagekey", "a".repeat(101), "instagram", "activity", "leader", 1000L);
}
