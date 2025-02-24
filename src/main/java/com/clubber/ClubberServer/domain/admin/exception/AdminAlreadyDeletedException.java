package com.clubber.ClubberServer.domain.admin.exception;

import com.clubber.ClubberServer.global.exception.BaseException;

public class AdminAlreadyDeletedException extends BaseException {

    public static final BaseException EXCEPTION = new AdminAlreadyDeletedException();

    private AdminAlreadyDeletedException() {
        super(AdminErrorCode.ADMIN_ALREADY_DELETED);}
}
