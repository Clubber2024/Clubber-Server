package com.clubber.ClubberServer.domain.admin.exception;

import com.clubber.ClubberServer.global.error.BaseErrorCode;
import com.clubber.ClubberServer.global.error.ErrorReason;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum AdminErrorCode implements BaseErrorCode {

    ADMIN_LOGIN_FAILED(HttpStatus.UNAUTHORIZED.value(), "ADMIN_401_1", "아이디 또는 비밀번호를 확인해주세요"),
    ADMIN_NOT_FOUND(HttpStatus.UNAUTHORIZED.value(), "ADMIN_401_2", "해당 관리자를 찾을 수 없습니다");

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
