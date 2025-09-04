package com.clubber.ClubberInternal.global.jwt.exception;


import com.clubber.ClubberInternal.global.exception.BaseException;

public class UserAlreadyDeletedException extends BaseException {

    public static final BaseException EXCEPTION = new UserAlreadyDeletedException();
    private UserAlreadyDeletedException() { super(UserErrorCode.USER_ALREADY_DELETED); }
}
