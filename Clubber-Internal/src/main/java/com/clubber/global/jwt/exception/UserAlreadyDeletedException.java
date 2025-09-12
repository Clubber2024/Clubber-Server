package com.clubber.global.jwt.exception;


import com.clubber.global.exception.BaseException;

public class UserAlreadyDeletedException extends BaseException {

    public static final BaseException EXCEPTION = new UserAlreadyDeletedException();
    private UserAlreadyDeletedException() { super(UserErrorCode.USER_ALREADY_DELETED); }
}
