package com.clubber.ClubberServer.domain.recruit.exception;

import com.clubber.ClubberServer.global.exception.BaseErrorCode;
import com.clubber.ClubberServer.global.exception.ErrorReason;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum RecruitErrorCode implements BaseErrorCode {
    RECRUIT_DELETE_REMAIN_IMAGE_DUPLICATED(HttpStatus.BAD_REQUEST.value(), "RECRUIT_400_1",
        "유지하는 이미지와 삭제하는 이미지가 중복됩니다."),
    RECRUIT_IMAGE_REVISE_FINAL_SIZE_DIFFERENT(HttpStatus.BAD_REQUEST.value(), "RECRUIT_400_2",
        "수정된 이미지 수와 실제 이미지 수가 다릅니다."),
    RECRUIT_CALENDAR_YEAR_INVALID(HttpStatus.BAD_REQUEST.value(), "RECRUIT_400_3",
        "유효하지 않은 연도입니다."),
    RECRUIT_CALENDAR_MONTH_INVALID(HttpStatus.BAD_REQUEST.value(), "RECRUIT_400_4",
        "유효하지 않은 월입니다."),
    RECRUIT_DATE_OUT_OF_ORDER(HttpStatus.BAD_REQUEST.value(), "RECRUIT_400_5", "시작일이 마감일보다 늦습니다."),
    RECRUIT_DATE_REQUIRED(HttpStatus.BAD_REQUEST.value(), "RECRUIT_400_6",
        "startAt과 endAt은 필수입니다."),
    RECRUIT_UNAUTHORIZED(HttpStatus.FORBIDDEN.value(), "RECRUIT_403_1", "모집글 접근 권한이 없습니다."),
    RECRUIT_DELETE_UNAUTHORIZED(HttpStatus.FORBIDDEN.value(), "RECRUIT_403_2", "모집글 삭제 권한이 없습니다."),
    RECRUIT_COMMENT_UNAUTHORIZED(HttpStatus.FORBIDDEN.value(), "RECRUIT_403_3", "댓글 권한이 없습니다."),
    RECRUIT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "RECRUIT_404_1", "해당 모집글이 존재하지 않습니다."),
    RECRUIT_IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "RECRUIT_404_2",
        "해당 모집글 이미지가 존재하지 않습니다."),
    RECRUIT_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "RECRUIT_404_3", "해당 댓글이 존재하지 않습니다.");

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
