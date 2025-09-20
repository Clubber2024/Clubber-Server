package com.clubber.domain.domains.review.exception;

import com.clubber.common.exception.BaseException;

public class ReviewAlreadyLikedException extends BaseException {
    public static final BaseException EXCEPTION = new ReviewAlreadyLikedException();

    public ReviewAlreadyLikedException() {
        super(ReviewErrorCode.REVIEW_ALREADY_LIKED);
    }
}
