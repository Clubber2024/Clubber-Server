package com.clubber.domain.domains.review.exception;

import com.clubber.common.exception.BaseException;

public class ReviewUserNotMatchException extends BaseException {
    public static final BaseException EXCEPTION = new ReviewUserNotMatchException();

    public ReviewUserNotMatchException() {
        super(ReviewErrorCode.REVIEW_USER_NOT_MATCHED);
    }
}
