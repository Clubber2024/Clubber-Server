package com.clubber.ClubberServer.domain.recruit.exception;

import com.clubber.ClubberServer.global.exception.BaseException;

public class RecruitUnauthorized extends BaseException {

    public static final BaseException EXCEPTION = new RecruitUnauthorized();

        private RecruitUnauthorized () {
            super(RecruitErrorCode.RECRUIT_UNAUTHORIZED);
        }
}


