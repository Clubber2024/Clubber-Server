package com.clubber.ClubberServer.domain.admin.exception;

import com.clubber.common.exception.BaseException;

public class AdminEqualsPreviousPasswordExcpetion extends BaseException {
	public static final BaseException EXCEPTION = new AdminEqualsPreviousPasswordExcpetion();
	public AdminEqualsPreviousPasswordExcpetion() {
		super(AdminErrorCode.ADMIN_EQUALS_PREVIOUS_PASSWORD);
	}
}
