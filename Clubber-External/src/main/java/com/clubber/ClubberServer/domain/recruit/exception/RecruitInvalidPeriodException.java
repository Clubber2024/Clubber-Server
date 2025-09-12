package com.clubber.ClubberServer.domain.recruit.exception;

import com.clubber.common.exception.BaseException;

public class RecruitInvalidPeriodException extends BaseException {

    public static final BaseException EXCEPTION = new RecruitInvalidPeriodException();

    private RecruitInvalidPeriodException() {
        super(RecruitErrorCode.RECRUIT_DATE_OUT_OF_ORDER);
    }

}
