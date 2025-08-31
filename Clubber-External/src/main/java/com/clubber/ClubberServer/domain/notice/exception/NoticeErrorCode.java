package com.clubber.ClubberServer.domain.notice.exception;

import com.clubber.ClubberServer.global.exception.BaseErrorCode;
import com.clubber.ClubberServer.global.exception.ErrorReason;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum NoticeErrorCode implements BaseErrorCode {
    NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "NOTICE_404_1", "해당 공지사항이 존재하지 않습니다.");

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
