package com.clubber.ClubberServer.domain.recruit.exception;

import com.clubber.ClubberServer.global.exception.BaseException;

public class RecruitDeleteUnauthorizedException extends BaseException {

    public static final BaseException EXCEPTION = new RecruitDeleteUnauthorizedException();
    private RecruitDeleteUnauthorizedException () {
        super(RecruitErrorCode.RECRUIT_DELETE_UNAUTHORIZED);
    }

}
