package com.clubber.ClubberServer.domain.user.exception;

import com.clubber.ClubberServer.global.error.BaseException;

public class RefreshTokenExpiredException extends BaseException {

    public static final BaseException EXCEPTION = new RefreshTokenExpiredException();
    private RefreshTokenExpiredException() { super(UserErrorCode.REFRESH_TOKEN_EXPIRED); }
}
