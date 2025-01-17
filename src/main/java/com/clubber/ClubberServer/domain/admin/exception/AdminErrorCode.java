package com.clubber.ClubberServer.domain.admin.exception;

import com.clubber.ClubberServer.global.exception.BaseErrorCode;
import com.clubber.ClubberServer.global.exception.ErrorReason;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum AdminErrorCode implements BaseErrorCode {


    INVALID_APPROVED_STATUS(HttpStatus.BAD_REQUEST.value(), "ADMIN_400_1", "승인 또는 미승인 할 수 없는 리뷰 상태입니다"),
    ADMIN_EQUALS_PREVIOUS_PASSWORD(HttpStatus.BAD_REQUEST.value(), "ADMIN_400_2", "이전 비밀번호와 다른 비밀번호를 작성해주세요."),
    ADMIN_ALREADY_DELETED(HttpStatus.BAD_REQUEST.value(), "ADMIN_400_2", "이미 탈퇴한 동아리 계정입니다"),

    ADMIN_LOGIN_FAILED(HttpStatus.UNAUTHORIZED.value(), "ADMIN_401_1", "아이디 또는 비밀번호를 확인해주세요"),
    ADMIN_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "ADMIN_404_1", "해당 관리자를 찾을 수 없습니다");


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
