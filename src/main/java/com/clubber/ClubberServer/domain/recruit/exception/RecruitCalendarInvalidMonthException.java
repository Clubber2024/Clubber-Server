package com.clubber.ClubberServer.domain.recruit.exception;

import com.clubber.ClubberServer.global.exception.BaseException;

public class RecruitCalendarInvalidMonthException extends BaseException {

    public static final BaseException EXCEPTION = new  RecruitCalendarInvalidMonthException();
    private  RecruitCalendarInvalidMonthException() {
        super(RecruitErrorCode.RECRUIT_CALENDAR_MONTH_INVALID);
    }

}