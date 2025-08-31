package com.clubber.ClubberServer.domain.calendar.exception;

import com.clubber.ClubberServer.global.exception.BaseException;

public class CalendarPostUnauthorizedException extends BaseException {

    public static final BaseException EXCEPTION = new CalendarPostUnauthorizedException();

    private CalendarPostUnauthorizedException() {
        super(CalendarErrorCode.CALENDAR_POST_UNAUTHORIZED);
    }

}
