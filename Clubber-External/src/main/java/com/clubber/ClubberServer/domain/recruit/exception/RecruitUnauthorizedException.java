package com.clubber.ClubberServer.domain.recruit.exception;

import com.clubber.common.exception.BaseException;

public class RecruitUnauthorizedException extends BaseException {

    public static final BaseException EXCEPTION = new RecruitUnauthorizedException();

        private RecruitUnauthorizedException () {
            super(RecruitErrorCode.RECRUIT_UNAUTHORIZED);
        }
}


