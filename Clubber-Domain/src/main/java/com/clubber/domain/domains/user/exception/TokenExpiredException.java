package com.clubber.domain.domains.user.exception;

import com.clubber.common.exception.BaseException;

public class TokenExpiredException extends BaseException {

    public static final BaseException EXCEPTION = new TokenExpiredException();
    private TokenExpiredException() { super(UserErrorCode.TOKEN_EXPIRED); }
}
