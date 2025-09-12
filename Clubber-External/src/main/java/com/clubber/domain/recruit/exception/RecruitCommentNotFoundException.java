package com.clubber.domain.recruit.exception;

import com.clubber.common.exception.BaseException;

public class RecruitCommentNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new RecruitCommentNotFoundException();
    private RecruitCommentNotFoundException () {
        super(RecruitErrorCode.RECRUIT_COMMENT_NOT_FOUND);
    }

}
