package com.clubber.domain.domains.review.exception;

import com.clubber.common.exception.BaseException;

public class ReviewLikeNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new ReviewLikeNotFoundException();

    private ReviewLikeNotFoundException() {
        super(ReviewErrorCode.REVIEW_LIKE_NOT_FOUND);
    }
}
