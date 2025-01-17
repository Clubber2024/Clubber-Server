package com.clubber.ClubberServer.domain.user.exception;

import com.clubber.ClubberServer.global.exception.BaseException;

public class TokenExpiredException extends BaseException {

    public static final BaseException EXCEPTION = new TokenExpiredException();
    private TokenExpiredException() { super(UserErrorCode.TOKEN_EXPIRED); }
}
