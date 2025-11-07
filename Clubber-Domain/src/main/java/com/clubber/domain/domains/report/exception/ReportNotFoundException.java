package com.clubber.domain.domains.report.exception;

import com.clubber.common.exception.BaseException;

public class ReportNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new ReportNotFoundException();

    public ReportNotFoundException() {
        super(ReportErrorCode.REVIEW_REPORT_NOT_FOUND);
    }
}