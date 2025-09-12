package com.clubber.global.jwt.exception;

import com.clubber.global.exception.BaseException;

public class TokenExpiredException extends BaseException {

    public static final BaseException EXCEPTION = new TokenExpiredException();
    private TokenExpiredException() { super(UserErrorCode.TOKEN_EXPIRED); }
}
