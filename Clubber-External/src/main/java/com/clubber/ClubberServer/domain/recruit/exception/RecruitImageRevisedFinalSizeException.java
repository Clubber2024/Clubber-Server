package com.clubber.ClubberServer.domain.recruit.exception;

import com.clubber.common.exception.BaseException;

public class RecruitImageRevisedFinalSizeException extends BaseException {

    public static final BaseException EXCEPTION = new RecruitImageRevisedFinalSizeException();

    private RecruitImageRevisedFinalSizeException() {
        super(RecruitErrorCode.RECRUIT_IMAGE_REVISE_FINAL_SIZE_DIFFERENT);
    }


}