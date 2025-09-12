package com.clubber.domain.admin.exception;

import static com.clubber.domain.admin.exception.AdminErrorCode.ADMIN_LOGIN_FAILED;

import com.clubber.common.exception.BaseException;

public class AdminLoginFailedException extends BaseException {

    public static final BaseException EXCEPTION = new AdminLoginFailedException();
    private AdminLoginFailedException() {
        super(ADMIN_LOGIN_FAILED);
    }
}
