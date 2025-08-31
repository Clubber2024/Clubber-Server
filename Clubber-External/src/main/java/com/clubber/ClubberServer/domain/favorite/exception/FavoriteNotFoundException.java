package com.clubber.ClubberServer.domain.favorite.exception;

import com.clubber.ClubberServer.global.exception.BaseException;

public class FavoriteNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new FavoriteNotFoundException();
    private FavoriteNotFoundException() { super(FavoriteErrorCode.FAVORITE_NOT_FOUND);}
}
