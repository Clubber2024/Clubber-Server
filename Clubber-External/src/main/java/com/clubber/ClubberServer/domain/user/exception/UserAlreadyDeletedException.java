package com.clubber.ClubberServer.domain.user.exception;

import com.clubber.ClubberServer.global.exception.BaseException;

public class UserAlreadyDeletedException extends BaseException {

    public static final BaseException EXCEPTION = new UserAlreadyDeletedException();
    private UserAlreadyDeletedException() { super(UserErrorCode.USER_ALREADY_DELETED); }
}
