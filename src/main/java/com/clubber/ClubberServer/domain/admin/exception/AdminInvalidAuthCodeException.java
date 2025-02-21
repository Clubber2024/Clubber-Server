package com.clubber.ClubberServer.domain.admin.exception;

import static com.clubber.ClubberServer.domain.admin.exception.AdminErrorCode.ADMIN_INVALID_AUTH_CODE;

import com.clubber.ClubberServer.global.exception.BaseException;

public class AdminInvalidAuthCodeException extends BaseException {

	public static final AdminInvalidAuthCodeException EXCEPTION = new AdminInvalidAuthCodeException();

	private AdminInvalidAuthCodeException() {
		super(ADMIN_INVALID_AUTH_CODE);
	}
}
