package com.clubber.ClubberServer.domain.admin.exception;

import com.clubber.ClubberServer.global.exception.BaseException;

public class AdminUsernameNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new AdminUsernameNotFoundException(); 

    private AdminUsernameNotFoundException() {
        super(AdminErrorCode.ADMIN_USERNAME_NOT_FOUND);
    }
}
