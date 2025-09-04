package com.clubber.ClubberServer.domain.admin.exception;


import com.clubber.ClubberServer.global.exception.BaseException;

public class AdminNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new AdminNotFoundException();

    private AdminNotFoundException() {
        super(AdminErrorCode.ADMIN_NOT_FOUND);
    }
}
