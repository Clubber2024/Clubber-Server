package com.clubber.ClubberServer.domain.favorite.exception;

import com.clubber.ClubberServer.global.error.BaseErrorCode;
import com.clubber.ClubberServer.global.error.BaseException;

public class ClubAlreadyRegisterdFavoriteException extends BaseException {

    public static final BaseException EXCEPTION = new ClubAlreadyRegisterdFavoriteException();

    private ClubAlreadyRegisterdFavoriteException() { super(FavoriteErrorCode.CLUB_ALREADY_REGISTERED_FAVORITE);}
}
