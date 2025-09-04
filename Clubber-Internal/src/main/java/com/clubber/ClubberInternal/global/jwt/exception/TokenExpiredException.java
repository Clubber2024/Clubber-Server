package com.clubber.ClubberInternal.global.jwt.exception;

import com.clubber.ClubberInternal.global.exception.BaseException;

public class TokenExpiredException extends BaseException {

    public static final BaseException EXCEPTION = new TokenExpiredException();
    private TokenExpiredException() { super(UserErrorCode.TOKEN_EXPIRED); }
}
