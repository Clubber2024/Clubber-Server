package com.clubber.domain.domains.admin.exception;


import com.clubber.common.exception.BaseException;

public class AdminNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new AdminNotFoundException();

    private AdminNotFoundException() {
        super(AdminErrorCode.ADMIN_NOT_FOUND);
    }
}
