package com.clubber.ClubberServer.domain.admin.exception;

import static com.clubber.ClubberServer.domain.admin.exception.AdminErrorCode.ADMIN_LOGIN_FAILED;

import com.clubber.ClubberServer.global.error.BaseErrorCode;
import com.clubber.ClubberServer.global.error.BaseException;

public class AdminLoginFailedException extends BaseException {

    public static final BaseException EXCEPTION = new AdminLoginFailedException();
    private AdminLoginFailedException() {
        super(ADMIN_LOGIN_FAILED);
    }
}
