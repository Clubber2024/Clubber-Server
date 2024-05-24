package com.clubber.ClubberServer.domain.club.exception;

import com.clubber.ClubberServer.global.error.BaseErrorCode;
import com.clubber.ClubberServer.global.error.ErrorReason;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ClubErrorCode implements BaseErrorCode {

    SEARCHED_CLUB_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "CLUB_404_1", "검색하신 동아리 및 소모임은 존재하지 않습니다."),
    DIVISION_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "CLUB_404_2", "해당 이름을 가진 분과가 존재하지 않습니다."),
    DEPARTMENT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "CLUB_404_3", "해당 이름을 가진 학과가 존재하지 않습니다."),
    CLUBID_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "CLUB_404_4", "해당 clubid을 가진 동아리가 존재하지 않습니다."),
    HASHTAG_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "CLUB_404_5", "해당 hashtag는 존재하지 않습니다.");



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
