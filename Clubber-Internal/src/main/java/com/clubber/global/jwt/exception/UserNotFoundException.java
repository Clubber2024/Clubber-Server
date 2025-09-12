package com.clubber.global.jwt.exception;


import com.clubber.global.exception.BaseException;

public class UserNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new UserNotFoundException();
    private UserNotFoundException() { super(UserErrorCode.USER_NOT_FOUND); }
}
