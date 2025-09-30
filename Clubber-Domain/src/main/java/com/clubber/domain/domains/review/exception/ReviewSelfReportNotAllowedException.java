package com.clubber.domain.domains.review.exception;

import com.clubber.common.exception.BaseException;

public class ReviewSelfReportNotAllowedException extends BaseException {

    public static final BaseException EXCEPTION = new ReviewSelfReportNotAllowedException();

    public ReviewSelfReportNotAllowedException() {
        super(ReviewErrorCode.REVIEW_SELF_REPORT_NOT_ALLOWED);
    }

}
