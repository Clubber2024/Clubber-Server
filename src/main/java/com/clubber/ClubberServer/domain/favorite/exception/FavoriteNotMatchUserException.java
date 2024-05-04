package com.clubber.ClubberServer.domain.favorite.exception;

import com.clubber.ClubberServer.global.error.BaseErrorCode;
import com.clubber.ClubberServer.global.error.BaseException;

public class FavoriteNotMatchUserException extends BaseException {

    public static final BaseException EXCEPTION = new FavoriteNotFoundException();

    public FavoriteNotMatchUserException() { super(FavoriteErrorCode.FAVORITE_NOT_MATCH_USER); }
}
