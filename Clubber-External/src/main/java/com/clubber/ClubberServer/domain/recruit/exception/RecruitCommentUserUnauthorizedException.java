package com.clubber.ClubberServer.domain.recruit.exception;

import com.clubber.common.exception.BaseException;

public class RecruitCommentUserUnauthorizedException extends BaseException {

    public static final BaseException EXCEPTION = new RecruitCommentUserUnauthorizedException();
    private RecruitCommentUserUnauthorizedException () {
        super(RecruitErrorCode.RECRUIT_COMMENT_UNAUTHORIZED);
    }

}
