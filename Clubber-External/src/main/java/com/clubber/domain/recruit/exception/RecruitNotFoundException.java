package com.clubber.domain.recruit.exception;
import com.clubber.common.exception.BaseException;

public class RecruitNotFoundException extends BaseException {
    public static final BaseException EXCEPTION = new RecruitNotFoundException();

    private RecruitNotFoundException() {
        super(RecruitErrorCode.RECRUIT_NOT_FOUND);
    }
}
