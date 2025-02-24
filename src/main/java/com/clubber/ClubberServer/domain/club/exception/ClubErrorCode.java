package com.clubber.ClubberServer.domain.club.exception;

import com.clubber.ClubberServer.global.exception.BaseErrorCode;
import com.clubber.ClubberServer.global.exception.ErrorReason;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ClubErrorCode implements BaseErrorCode {

    CLUB_ALREADY_DELETED(HttpStatus.BAD_REQUEST.value(), "CLUB_400_1", "이미 삭제된 동아리입니다."),
    CLUB_NOT_AGREED_TO_PROVIDE_INFO(HttpStatus.FORBIDDEN.value(), "CLUB_403_1", "정보 제공에 동의하지 않은 동아리입니다."),
    CLUB_NOT_AGREE_TO_PROVIDE_REVIEW(HttpStatus.FORBIDDEN.value(), "CLUB_403_2", "리뷰 제공에 동의하지 않은 동아리입니다."),
    SEARCHED_CLUB_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "CLUB_404_1", "검색하신 동아리 및 소모임은 존재하지 않습니다."),
    DIVISION_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "CLUB_404_2", "해당 이름을 가진 분과가 존재하지 않습니다."),
    DEPARTMENT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "CLUB_404_3", "해당 이름을 가진 학과가 존재하지 않습니다."),
    CLUBID_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "CLUB_404_4", "해당 clubid을 가진 동아리가 존재하지 않습니다."),
    HASHTAG_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "CLUB_404_5", "해당 hashtag에 속한 동아리가 존재하지 않습니다.");



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
