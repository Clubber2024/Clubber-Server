package com.clubber.ClubberServer.domain.favorite.exception;

import com.clubber.ClubberServer.global.error.BaseErrorCode;
import com.clubber.ClubberServer.global.error.ErrorReason;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum FavoriteErrorCode implements BaseErrorCode {

    CLUB_ALREADY_REGISTERED_FAVORITE(HttpStatus.BAD_REQUEST.value(), "FAVORITE_400_1", "즐겨찾기에 이미 등록한 동아리입니다.");

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
