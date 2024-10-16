package com.clubber.ClubberServer.util.fixture;

import com.clubber.ClubberServer.domain.admin.dto.CreateAdminsLoginRequest;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsPasswordRequest;

public class AdminFixture {
	public static final CreateAdminsLoginRequest VALID_ADMIN_REQUEST = new CreateAdminsLoginRequest("동아리 1","비밀번호 1");

	public static final UpdateAdminsPasswordRequest VALID_UPDATE_PASSWORD_REQUEST = new UpdateAdminsPasswordRequest("수정비밀번호");
}
