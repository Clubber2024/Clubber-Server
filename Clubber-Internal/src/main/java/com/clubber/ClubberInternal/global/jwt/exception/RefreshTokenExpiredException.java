package com.clubber.ClubberInternal.global.jwt.exception;


import com.clubber.ClubberInternal.global.exception.BaseException;

public class RefreshTokenExpiredException extends BaseException {

    public static final BaseException EXCEPTION = new RefreshTokenExpiredException();
    private RefreshTokenExpiredException() { super(UserErrorCode.REFRESH_TOKEN_EXPIRED); }
}
