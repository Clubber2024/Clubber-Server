package com.clubber.ClubberServer.domain.recruit.exception;

import com.clubber.ClubberServer.global.exception.BaseException;

public class RecruitDeleteUnauthorized extends BaseException {

    public static final BaseException EXCEPTION = new RecruitDeleteUnauthorized();
    private RecruitDeleteUnauthorized () {
        super(RecruitErrorCode.RECRUIT_DELETE_UNAUTHORIZED);
    }

}
