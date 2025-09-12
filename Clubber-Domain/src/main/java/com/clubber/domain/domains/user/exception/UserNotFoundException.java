package com.clubber.domain.domains.user.exception;

import com.clubber.common.exception.BaseException;

public class UserNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new UserNotFoundException();
    private UserNotFoundException() { super(UserErrorCode.USER_NOT_FOUND); }
}
