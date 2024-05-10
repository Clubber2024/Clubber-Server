package com.clubber.ClubberServer.domain.user.exception;

import com.clubber.ClubberServer.global.error.BaseErrorCode;
import com.clubber.ClubberServer.global.error.BaseException;

public class UserAlreadyDeletedException extends BaseException {

    public static final BaseException EXCEPTION = new UserAlreadyDeletedException();
    public UserAlreadyDeletedException() { super(UserErrorCode.USER_ALREADY_DELETED); }
}
