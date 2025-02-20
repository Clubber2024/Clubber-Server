package com.clubber.ClubberServer.domain.recruit.exception;

import com.clubber.ClubberServer.global.exception.BaseException;

public class RecruitCalendarInvalidYearException extends BaseException {

    public static final BaseException EXCEPTION = new RecruitCalendarInvalidYearException();

    private RecruitCalendarInvalidYearException() {
        super(RecruitErrorCode.RECRUIT_CALENDAR_YEAR_INVALID);
    }

}