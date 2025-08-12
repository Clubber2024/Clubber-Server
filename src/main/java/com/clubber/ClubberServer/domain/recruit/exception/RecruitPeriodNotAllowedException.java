package com.clubber.ClubberServer.domain.recruit.exception;

import com.clubber.ClubberServer.global.exception.BaseException;

public class RecruitPeriodNotAllowedException extends BaseException {

    public static final BaseException EXCEPTION = new RecruitPeriodNotAllowedException();

    private RecruitPeriodNotAllowedException() {
        super(RecruitErrorCode.RECRUIT_PERIOD_NOT_ALLOWED);
    }

}
