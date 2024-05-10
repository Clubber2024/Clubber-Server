package com.clubber.ClubberServer.domain.favorite.exception;

import com.clubber.ClubberServer.global.error.BaseErrorCode;
import com.clubber.ClubberServer.global.error.BaseException;

public class FavoriteNotMatchClubException extends BaseException {

    public static final BaseException EXCEPTION = new FavoriteNotMatchClubException();

    public FavoriteNotMatchClubException() { super(FavoriteErrorCode.FAVORITE_NOT_MATCH_CLUB); }
}
