package com.clubber.ClubberServer.domain.club.exception;

import com.clubber.ClubberServer.global.error.BaseErrorCode;
import com.clubber.ClubberServer.global.error.ErrorReason;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ClubErrorCode implements BaseErrorCode {

    SEARCHED_CLUB_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "SEARCH_CLUB_404_1", "검색하신 동아리 및 소모임은 존재하지 않습니다.");

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
