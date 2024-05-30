package com.clubber.ClubberServer.domain.review.exception;

import com.clubber.ClubberServer.global.error.BaseErrorCode;
import com.clubber.ClubberServer.global.error.BaseException;

public class ReviewEnumNotMatchedException extends BaseException {

    public static final BaseException EXCEPTION = new ReviewEnumNotMatchedException();
    public ReviewEnumNotMatchedException() {
        super(ReviewErrorCode.REVIEW_KEYWORD_ENUM_NOT_MATCHED);
    }
}
