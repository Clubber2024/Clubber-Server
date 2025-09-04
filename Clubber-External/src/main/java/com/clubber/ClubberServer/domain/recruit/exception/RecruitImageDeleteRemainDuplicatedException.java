package com.clubber.ClubberServer.domain.recruit.exception;

import com.clubber.ClubberServer.global.exception.BaseException;

public class RecruitImageDeleteRemainDuplicatedException extends BaseException {

    public static final BaseException EXCEPTION = new RecruitImageDeleteRemainDuplicatedException();

    private RecruitImageDeleteRemainDuplicatedException() {
        super(RecruitErrorCode.RECRUIT_DELETE_REMAIN_IMAGE_DUPLICATED);
    }


}
