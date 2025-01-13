package com.clubber.ClubberServer.domain.favorite.exception;

import com.clubber.ClubberServer.global.exception.BaseException;

public class FavoriteNotMatchUserException extends BaseException {

    public static final BaseException EXCEPTION = new FavoriteNotMatchUserException();

    private FavoriteNotMatchUserException() { super(FavoriteErrorCode.FAVORITE_NOT_MATCH_USER); }
}
