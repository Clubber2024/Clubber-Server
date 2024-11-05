package com.clubber.ClubberServer.domain.user.exception;

import com.clubber.ClubberServer.global.error.BaseErrorCode;
import com.clubber.ClubberServer.global.error.ErrorReason;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum UserErrorCode implements BaseErrorCode {

    USER_ALREADY_DELETED(HttpStatus.BAD_REQUEST.value(), "USER_400_1", "이미 탈퇴한 유저입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED.value(), "USER_401_1", "잘못된 토큰입니다. 재로그인 해주세요"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED.value(), "USER_401_1", "인증 시간이 만료되었습니다. 인증토큰을 재 발급 해주세요"),
    REFRESH_TOKEN_EXPIRED(HttpStatus.FORBIDDEN.value(), "USER_403_1","로그인 정보가 만료되었습니다. 다시 로그인 해주세요."),

    ACCESS_TOKEN_NOT_EXIST(HttpStatus.FORBIDDEN.value(), "USER_404_1", "알맞은 Access Token을 넣어주세요"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "USER_404_1", "존재하지 않는 유저입니다.");

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.builder()
                .status(status)
                .code(code)
                .reason(reason).build();
    }

    private final Integer status;
    private final String code;
    private final String reason;
}
