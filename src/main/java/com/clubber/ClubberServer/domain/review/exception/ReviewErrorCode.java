package com.clubber.ClubberServer.domain.review.exception;

import com.clubber.ClubberServer.global.error.BaseErrorCode;
import com.clubber.ClubberServer.global.error.ErrorReason;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {
    USER_ALREADY_REVIEWD(HttpStatus.BAD_REQUEST.value() , "REVIEW_400_1", "이미 리뷰를 등록한 동아리입니다."),
    REVIEW_KEYWORD_ENUM_NOT_MATCHED(HttpStatus.BAD_REQUEST.value(), "REVIEW_400_2", "잘못된 리뷰 키워드 값입니다.");

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
