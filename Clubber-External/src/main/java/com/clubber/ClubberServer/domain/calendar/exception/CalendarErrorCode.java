package com.clubber.ClubberServer.domain.calendar.exception;

import com.clubber.common.exception.BaseErrorCode;
import com.clubber.common.exception.ErrorReason;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum CalendarErrorCode implements BaseErrorCode {
    CALENDAR_MONTH_INVALID(HttpStatus.BAD_REQUEST.value(), "CALENDAR_400_1", "유효하지 않은 월입니다."),
    CALENDAR_POST_UNAUTHORIZED(HttpStatus.FORBIDDEN.value(), "CALENDAR_403_1", "캘린더 작성 권한이 없습니다."),
    CALENDAR_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "CALENDAR_404_1", "존재하지 않는 캘린더입니다");


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
