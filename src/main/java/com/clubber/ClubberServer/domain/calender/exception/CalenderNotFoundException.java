package com.clubber.ClubberServer.domain.calender.exception;

import com.clubber.ClubberServer.global.exception.BaseException;

public class CalenderNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new CalenderNotFoundException();

    private CalenderNotFoundException() {
        super(CalenderErrorCode.CALENDER_NOT_FOUND);
    }
}
