package com.clubber.domain.domains.favorite.exception;

import com.clubber.common.exception.BaseException;

public class FavoriteNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new FavoriteNotFoundException();
    private FavoriteNotFoundException() { super(FavoriteErrorCode.FAVORITE_NOT_FOUND);}
}
