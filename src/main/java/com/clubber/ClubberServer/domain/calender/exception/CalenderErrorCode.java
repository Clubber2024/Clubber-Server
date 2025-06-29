package com.clubber.ClubberServer.domain.calender.exception;

import com.clubber.ClubberServer.global.exception.BaseErrorCode;
import com.clubber.ClubberServer.global.exception.ErrorReason;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum CalenderErrorCode implements BaseErrorCode {
    CALENDER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "CALENDER_404_1", "존재하지 않는 캘린더입니다");

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
