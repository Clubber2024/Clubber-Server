package com.clubber.global.jwt.exception;


import com.clubber.global.exception.BaseException;

public class InvalidTokenException extends BaseException {

    public static final BaseException EXCEPTION = new InvalidTokenException();
    private InvalidTokenException() { super(UserErrorCode.INVALID_TOKEN); }
}
