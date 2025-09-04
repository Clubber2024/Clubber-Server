package com.clubber.ClubberInternal.global.jwt.exception;


import com.clubber.ClubberInternal.global.exception.BaseException;

public class InvalidTokenException extends BaseException {

    public static final BaseException EXCEPTION = new InvalidTokenException();
    private InvalidTokenException() { super(UserErrorCode.INVALID_TOKEN); }
}
