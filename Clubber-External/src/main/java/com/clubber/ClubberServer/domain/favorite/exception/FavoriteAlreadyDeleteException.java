package com.clubber.ClubberServer.domain.favorite.exception;

import com.clubber.common.exception.BaseException;

public class FavoriteAlreadyDeleteException extends BaseException {

    public static final BaseException EXCEPTION = new FavoriteAlreadyDeleteException();
    private FavoriteAlreadyDeleteException() { super(FavoriteErrorCode.FAVORITE_ALREADY_DELETE); }
}
