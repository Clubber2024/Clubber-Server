package com.clubber.domain.domains.report.exception;

import com.clubber.common.exception.BaseErrorCode;
import com.clubber.common.exception.ErrorReason;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ReportErrorCode implements BaseErrorCode {

    REVIEW_REPORT_ALREADY_HIDDEN(HttpStatus.BAD_REQUEST.value(), "REVIEW_REPORT_400_1",
        "이미 숨김 처리된 리뷰입니다."),
    DETAIL_REASON_REQUIRED(HttpStatus.BAD_REQUEST.value(), "REVIEW_REPORT_400_2",
        "신고 사유를 입력해야합니다."),
    REVIEW_REPORT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "REVIEW_REPORT_404_1",
        "존재하지 않는 신고내역입니다.");


    private final Integer status;
    private final String code;
    private final String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.builder()
            .status(status)
            .code(code)
            .reason(reason)
            .build();
    }
}
