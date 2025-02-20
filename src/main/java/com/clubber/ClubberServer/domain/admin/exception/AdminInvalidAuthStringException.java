package com.clubber.ClubberServer.domain.admin.exception;

import static com.clubber.ClubberServer.domain.admin.exception.AdminErrorCode.ADMIN_INVALID_AUTH_STRING;

import com.clubber.ClubberServer.global.exception.BaseException;

public class AdminInvalidAuthStringException extends BaseException {

	public static final AdminInvalidAuthStringException EXCEPTION = new AdminInvalidAuthStringException();

	private AdminInvalidAuthStringException() {
		super(ADMIN_INVALID_AUTH_STRING);
	}
}
