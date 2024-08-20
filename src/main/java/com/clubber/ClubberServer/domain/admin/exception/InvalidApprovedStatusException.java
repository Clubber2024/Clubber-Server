package com.clubber.ClubberServer.domain.admin.exception;



import com.clubber.ClubberServer.global.error.BaseException;


public class InvalidApprovedStatusException extends BaseException {

    public static final BaseException EXCEPTION = new InvalidApprovedStatusException();
    private InvalidApprovedStatusException() {
        super(AdminErrorCode.INVALID_APPROVED_STATUS);
    }
}
