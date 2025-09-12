package com.clubber.domain.admin.exception;

import com.clubber.common.exception.BaseException;

public class AdminAlreadyEmailVerifiedException extends BaseException {

	public static final BaseException EXCEPTION = new AdminAlreadyEmailVerifiedException();

	public AdminAlreadyEmailVerifiedException() {
		super(AdminErrorCode.ADMIN_ALREADY_EMAIL_VERIFIED);
	}
}
