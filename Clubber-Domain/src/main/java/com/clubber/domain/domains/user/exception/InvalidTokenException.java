package com.clubber.domain.domains.user.exception;

import com.clubber.common.exception.BaseException;

public class InvalidTokenException extends BaseException {

    public static final BaseException EXCEPTION = new InvalidTokenException();
    private InvalidTokenException() { super(UserErrorCode.INVALID_TOKEN); }
}
