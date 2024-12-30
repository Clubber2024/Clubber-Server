package com.clubber.ClubberServer.domain.recruit.exception;

import com.clubber.ClubberServer.global.error.BaseErrorCode;
import com.clubber.ClubberServer.global.error.ErrorReason;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
public enum RecruitErrorCode implements BaseErrorCode {
    RECRUIT_UNAUTHORIZED(HttpStatus.FORBIDDEN.value(),"RECRUIT_403_1", "모집글 접근 권한이 없습니다."),
    RECRUIT_DELETE_UNAUTHORIZED(HttpStatus.FORBIDDEN.value(),"RECRUIT_403_2", "모집글 삭제 권한이 없습니다."),
    RECRUIT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "RECRUIT_404_1", "해당 모집글이 존재하지 않습니다.");

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
