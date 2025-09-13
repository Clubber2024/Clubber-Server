package com.clubber.domain.domains.user.exception;

import com.clubber.common.exception.BaseException;

public class UserAlreadyDeletedException extends BaseException {

    public static final BaseException EXCEPTION = new UserAlreadyDeletedException();
    private UserAlreadyDeletedException() { super(UserErrorCode.USER_ALREADY_DELETED); }
}
