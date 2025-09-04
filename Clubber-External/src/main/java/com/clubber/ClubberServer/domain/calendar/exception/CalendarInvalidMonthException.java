package com.clubber.ClubberServer.domain.calendar.exception;

import com.clubber.ClubberServer.global.exception.BaseException;

public class CalendarInvalidMonthException extends BaseException {

    public static final BaseException EXCEPTION = new CalendarInvalidMonthException();
    private CalendarInvalidMonthException() {
        super(CalendarErrorCode.CALENDAR_MONTH_INVALID);
    }

}