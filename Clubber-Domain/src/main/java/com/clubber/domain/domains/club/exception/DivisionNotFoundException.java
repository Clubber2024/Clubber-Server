package com.clubber.domain.domains.club.exception;

import com.clubber.common.exception.BaseException;

public class DivisionNotFoundException extends BaseException {
    public static final BaseException EXCEPTION = new DivisionNotFoundException();

    private DivisionNotFoundException() {
        super(ClubErrorCode.DIVISION_NOT_FOUND);
    }
}
