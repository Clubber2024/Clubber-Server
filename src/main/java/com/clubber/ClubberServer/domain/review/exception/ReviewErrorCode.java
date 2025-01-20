package com.clubber.ClubberServer.domain.review.exception;

import com.clubber.ClubberServer.global.exception.BaseErrorCode;
import com.clubber.ClubberServer.global.exception.ErrorReason;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {
    USER_ALREADY_REVIEWD(HttpStatus.BAD_REQUEST.value() , "REVIEW_400_1", "이미 리뷰를 등록한 동아리입니다."),
    REVIEW_CLUB_NOT_MATCHED(HttpStatus.BAD_REQUEST.value(), "REVIEW_400_2", "리뷰와 동아리가 일치하지 않습니다"),
    REVIEW_KEYWORD_ENUM_NOT_MATCHED(HttpStatus.BAD_REQUEST.value(), "REVIEW_400_3", "잘못된 리뷰 키워드 값입니다."),
    USER_REVIEWS_NOT_FOUND(HttpStatus.BAD_REQUEST.value(),"REVIEW_400_4","적절하지 않은 리뷰 ID입니다."),
    REVIEW_ALREADY_DELETED(HttpStatus.BAD_REQUEST.value(), "REVIEW_400_5", "이미 삭제된 리뷰입니다.");

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
