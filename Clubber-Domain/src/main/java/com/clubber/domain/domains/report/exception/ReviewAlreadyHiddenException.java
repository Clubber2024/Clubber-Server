package com.clubber.domain.domains.report.exception;

import com.clubber.common.exception.BaseException;

public class ReviewAlreadyHiddenException extends BaseException {

    public static final BaseException EXCEPTION = new ReviewAlreadyHiddenException();

    public ReviewAlreadyHiddenException() {
        super(ReportErrorCode.REVIEW_REPORT_NOT_FOUND);
    }
}


