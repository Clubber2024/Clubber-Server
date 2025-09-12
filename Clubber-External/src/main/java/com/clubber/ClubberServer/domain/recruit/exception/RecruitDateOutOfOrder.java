package com.clubber.ClubberServer.domain.recruit.exception;

import com.clubber.common.exception.BaseException;

public class RecruitDateOutOfOrder extends BaseException {

    public static final BaseException EXCEPTION = new RecruitDateOutOfOrder();

    private RecruitDateOutOfOrder() {
        super(RecruitErrorCode.RECRUIT_DATE_OUT_OF_ORDER);
    }

}