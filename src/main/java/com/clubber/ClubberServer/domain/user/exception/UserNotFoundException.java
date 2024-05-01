package com.clubber.ClubberServer.domain.user.exception;

import com.clubber.ClubberServer.global.error.BaseErrorCode;
import com.clubber.ClubberServer.global.error.BaseException;

public class UserNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new UserNotFoundException();
    public UserNotFoundException() { super(UserErrorCode.USER_NOT_FOUND); }
}
