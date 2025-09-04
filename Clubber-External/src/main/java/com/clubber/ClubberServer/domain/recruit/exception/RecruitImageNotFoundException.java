package com.clubber.ClubberServer.domain.recruit.exception;

import com.clubber.ClubberServer.global.exception.BaseException;

public class RecruitImageNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new RecruitImageNotFoundException ();

    private RecruitImageNotFoundException() {
        super(RecruitErrorCode.RECRUIT_IMAGE_NOT_FOUND);
    }


}