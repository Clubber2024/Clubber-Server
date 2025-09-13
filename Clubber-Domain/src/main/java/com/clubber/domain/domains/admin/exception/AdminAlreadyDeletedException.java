package com.clubber.domain.domains.admin.exception;

import com.clubber.common.exception.BaseException;

public class AdminAlreadyDeletedException extends BaseException {

    public static final BaseException EXCEPTION = new AdminAlreadyDeletedException();

    private AdminAlreadyDeletedException() {
        super(AdminErrorCode.ADMIN_ALREADY_DELETED);}
}
