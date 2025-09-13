package com.clubber.domain.recruit.exception;

import com.clubber.common.exception.BaseException;

public class RecruitMissingPeriodException extends BaseException {

    public static final BaseException EXCEPTION = new RecruitMissingPeriodException();

    private RecruitMissingPeriodException() {
        super(RecruitErrorCode.RECRUIT_DATE_REQUIRED);
    }

}
