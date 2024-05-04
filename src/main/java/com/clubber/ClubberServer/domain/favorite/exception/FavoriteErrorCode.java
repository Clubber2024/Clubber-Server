package com.clubber.ClubberServer.domain.favorite.exception;

import com.clubber.ClubberServer.global.error.BaseErrorCode;
import com.clubber.ClubberServer.global.error.ErrorReason;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum FavoriteErrorCode implements BaseErrorCode {

    CLUB_ALREADY_REGISTERED_FAVORITE(HttpStatus.BAD_REQUEST.value(), "FAVORITE_400_1", "즐겨찾기에 이미 등록한 동아리입니다."),
    FAVORITE_NOT_MATCH_CLUB(HttpStatus.BAD_REQUEST.value(), "FAVORITE_400_2", "즐겨찾기와 동아리가 맞지 않습니다"),
    FAVORITE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "FAVORITE_404_1", "존재하지 않는 즐겨찾기입니다");

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
