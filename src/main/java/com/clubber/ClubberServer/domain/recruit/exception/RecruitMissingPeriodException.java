package com.clubber.ClubberServer.domain.recruit.exception;

import com.clubber.ClubberServer.global.exception.BaseException;

public class RecruitMissingPeriodException extends BaseException {

    public static final BaseException EXCEPTION = new RecruitMissingPeriodException();

    private RecruitMissingPeriodException() {
        super(RecruitErrorCode.RECRUIT_INVALID_DATE);
    }

}
