package com.clubber.ClubberServer.domain.admin.exception;

import com.clubber.ClubberServer.global.exception.BaseException;

public class AdminInvalidCurrentPasswordException extends BaseException {
    public static final BaseException EXCEPTION = new AdminInvalidCurrentPasswordException();

    private AdminInvalidCurrentPasswordException() {
        super(AdminErrorCode.ADMIN_INVALID_CURRENT_PASSWORD);
    }
}
