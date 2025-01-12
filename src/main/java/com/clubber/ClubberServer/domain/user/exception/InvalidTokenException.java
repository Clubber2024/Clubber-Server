package com.clubber.ClubberServer.domain.user.exception;

import com.clubber.ClubberServer.global.exception.BaseException;

public class InvalidTokenException extends BaseException {

    public static final BaseException EXCEPTION = new InvalidTokenException();
    private InvalidTokenException() { super(UserErrorCode.INVALID_TOKEN); }
}
