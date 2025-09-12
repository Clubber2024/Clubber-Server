package com.clubber.domain.domains.user.exception;

import com.clubber.common.exception.BaseException;

public class RefreshTokenExpiredException extends BaseException {

    public static final BaseException EXCEPTION = new RefreshTokenExpiredException();
    private RefreshTokenExpiredException() { super(UserErrorCode.REFRESH_TOKEN_EXPIRED); }
}
