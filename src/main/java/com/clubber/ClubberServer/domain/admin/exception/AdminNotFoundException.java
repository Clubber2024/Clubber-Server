package com.clubber.ClubberServer.domain.admin.exception;

import com.clubber.ClubberServer.global.error.BaseException;
import static com.clubber.ClubberServer.domain.admin.exception.AdminErrorCode.ADMIN_NOT_FOUND;

public class AdminNotFoundException extends BaseException{
    public static final BaseException EXCEPTION = new AdminNotFoundException();
    private AdminNotFoundException() {
        super(ADMIN_NOT_FOUND);
    }
}
