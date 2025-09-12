package com.clubber.ClubberServer.domain.favorite.exception;

import com.clubber.common.exception.BaseException;

public class ClubAlreadyRegisterdFavoriteException extends BaseException {

    public static final BaseException EXCEPTION = new ClubAlreadyRegisterdFavoriteException();

    private ClubAlreadyRegisterdFavoriteException() { super(FavoriteErrorCode.CLUB_ALREADY_REGISTERED_FAVORITE);}
}
