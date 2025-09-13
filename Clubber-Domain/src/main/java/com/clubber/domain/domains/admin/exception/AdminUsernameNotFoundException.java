package com.clubber.domain.domains.admin.exception;

import com.clubber.common.exception.BaseException;

public class AdminUsernameNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new AdminUsernameNotFoundException(); 

    private AdminUsernameNotFoundException() {
        super(AdminErrorCode.ADMIN_USERNAME_NOT_FOUND);
    }
}
