package com.clubber.domain.domains.review.exception;

import com.clubber.common.exception.BaseErrorCode;
import com.clubber.common.exception.ErrorReason;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {
    USER_ALREADY_REVIEWED(HttpStatus.BAD_REQUEST.value(), "REVIEW_400_1", "이미 리뷰를 등록한 동아리입니다."),
    REVIEW_CLUB_NOT_MATCHED(HttpStatus.BAD_REQUEST.value(), "REVIEW_400_2", "리뷰와 동아리가 일치하지 않습니다"),
    REVIEW_KEYWORD_ENUM_NOT_MATCHED(HttpStatus.BAD_REQUEST.value(), "REVIEW_400_3",
            "잘못된 리뷰 키워드 값입니다."),
    USER_REVIEWS_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "REVIEW_400_4", "적절하지 않은 리뷰 ID입니다."),
    REVIEW_ALREADY_DELETED(HttpStatus.BAD_REQUEST.value(), "REVIEW_400_5", "이미 삭제된 리뷰입니다."),
    REVIEW_ALREADY_VERIFIED(HttpStatus.BAD_REQUEST.value(), "REVIEW_400_6", "이미 인증된 리뷰입니다."),
    REVIEW_USER_NOT_MATCHED(HttpStatus.BAD_REQUEST.value(), "REVIEW_400_7", "리뷰의 작성자와 일치하지 않습니다"),
    REVIEW_ALREADY_LIKED(HttpStatus.BAD_REQUEST.value(), "REVIEW_400_8", "이미 좋아요를 표시한 리뷰입니다."),
    REVIEW_SELF_REPORT_NOT_ALLOWED(HttpStatus.BAD_REQUEST.value(), "REVIEW_400_9", "자신이 작성한 리뷰는 신고할 수 없습니다."),
    REVIEW_ALREADY_HIDDEN(HttpStatus.BAD_REQUEST.value(),"REVIEW_400_10", "이미 숨김 처리된 리뷰입니다."),
    REVIEW_HAS_REPORT(HttpStatus.BAD_REQUEST.value(), "REVIEW_400_11", "해당 리뷰에 대한 신고 내역이 존재하므로 수정이 불가능합니다."),
    REVIEW_NOT_FOUND(HttpStatus.NO_CONTENT.value(), "REVIEW_404_1", "존재하지 않는 리뷰입니다."),
    REVIEW_LIKE_NOT_FOUND(HttpStatus.NO_CONTENT.value(), "REVIEW_404_2", "좋아요하지 않은 리뷰입니다");

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
