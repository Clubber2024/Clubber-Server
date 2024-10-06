package com.clubber.ClubberServer.domain.recruit.exception;

import com.clubber.ClubberServer.global.error.BaseException;

public class RecruitPostUnauthorized extends BaseException {

    public static final BaseException EXCEPTION = new RecruitPostUnauthorized();
    private RecruitPostUnauthorized () {
        super(RecruitErrorCode.RECRUIT_POST_UNAUTHORIZED);
    }
}
