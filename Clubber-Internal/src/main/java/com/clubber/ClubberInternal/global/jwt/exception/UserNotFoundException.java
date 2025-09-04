package com.clubber.ClubberInternal.global.jwt.exception;


import com.clubber.ClubberInternal.global.exception.BaseException;

public class UserNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new UserNotFoundException();
    private UserNotFoundException() { super(UserErrorCode.USER_NOT_FOUND); }
}
