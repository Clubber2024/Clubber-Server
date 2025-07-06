package com.clubber.ClubberServer.domain.recruit.exception;

import com.clubber.ClubberServer.global.exception.BaseException;

public class RecruitInvalidPeriodException extends BaseException {

    public static final BaseException EXCEPTION = new RecruitInvalidPeriodException();

    private RecruitInvalidPeriodException() {
        super(RecruitErrorCode.RECRUIT_CALENDAR_MONTH_INVALID);
    }

}
