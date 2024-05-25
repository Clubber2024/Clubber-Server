package com.clubber.ClubberServer.global.error;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum GlobalErrorCode implements BaseErrorCode {

    PAGE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "GLOBAL_404_1","페이지를 찾을 수 없습니다.");

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
