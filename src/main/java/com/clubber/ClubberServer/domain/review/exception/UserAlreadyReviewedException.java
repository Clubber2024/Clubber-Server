package com.clubber.ClubberServer.domain.review.exception;

import com.clubber.ClubberServer.global.error.BaseException;

public class UserAlreadyReviewedException extends BaseException {

    public static final BaseException EXCEPTION = new UserAlreadyReviewedException();
    private UserAlreadyReviewedException() {
        super(ReviewErrorCode.USER_ALREADY_REVIEWD);
    }
}
