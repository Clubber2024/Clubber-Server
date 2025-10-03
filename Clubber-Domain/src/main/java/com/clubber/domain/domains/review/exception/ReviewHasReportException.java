package com.clubber.domain.domains.review.exception;

import com.clubber.common.exception.BaseException;

public class ReviewHasReportException extends BaseException {
    public static final BaseException EXCEPTION = new ReviewHasReportException();

    private ReviewHasReportException() {
        super(ReviewErrorCode.REVIEW_HAS_REPORT);
    }
}
