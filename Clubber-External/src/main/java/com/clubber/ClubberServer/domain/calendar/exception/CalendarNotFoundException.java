package com.clubber.ClubberServer.domain.calendar.exception;

import com.clubber.ClubberServer.global.exception.BaseException;

public class CalendarNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new CalendarNotFoundException();

    private CalendarNotFoundException() {
        super(CalendarErrorCode.CALENDAR_NOT_FOUND);
    }
}
