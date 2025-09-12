package com.clubber.ClubberServer.domain.favorite.exception;

import com.clubber.common.exception.BaseException;

public class FavoriteNotMatchClubException extends BaseException {

    public static final BaseException EXCEPTION = new FavoriteNotMatchClubException();

    private FavoriteNotMatchClubException() { super(FavoriteErrorCode.FAVORITE_NOT_MATCH_CLUB); }
}
