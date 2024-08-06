package com.clubber.ClubberServer.domain.recruit.exception;
import com.clubber.ClubberServer.global.error.BaseException;

public class RecruitNotFoundException extends BaseException {
    public static final BaseException EXCEPTION = new RecruitNotFoundException();

    private RecruitNotFoundException() {
        super(RecruitErrorCode.RECRUIT_NOT_FOUND);
    }
}
