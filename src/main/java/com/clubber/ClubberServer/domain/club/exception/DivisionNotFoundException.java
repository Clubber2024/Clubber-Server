package com.clubber.ClubberServer.domain.club.exception;

import com.clubber.ClubberServer.global.error.BaseException;

public class DivisionNotFoundException extends BaseException {
    public static final BaseException EXCEPTION = new DivisionNotFoundException();

    public DivisionNotFoundException() {
        super(ClubErrorCode.DIVISION_NOT_FOUND);
    }
}
