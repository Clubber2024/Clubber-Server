package com.clubber.domain.admin.exception;



import com.clubber.common.exception.BaseException;


public class InvalidApprovedStatusException extends BaseException {

    public static final BaseException EXCEPTION = new InvalidApprovedStatusException();
    private InvalidApprovedStatusException() {
        super(AdminErrorCode.INVALID_APPROVED_STATUS);
    }
}
