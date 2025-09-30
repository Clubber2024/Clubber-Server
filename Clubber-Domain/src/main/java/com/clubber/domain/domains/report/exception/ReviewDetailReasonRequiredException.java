package com.clubber.domain.domains.report.exception;

import com.clubber.common.exception.BaseException;

public class ReviewDetailReasonRequiredException extends BaseException {

    public static final BaseException EXCEPTION = new ReviewDetailReasonRequiredException();

    public ReviewDetailReasonRequiredException() {
        super(ReportErrorCode.DETAIL_REASON_REQUIRED);
    }
}
