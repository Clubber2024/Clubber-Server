package com.clubber.ClubberServer.domain.club.exception;

import com.clubber.ClubberServer.domain.favorite.exception.ClubAlreadyRegisterdFavoriteException;
import com.clubber.ClubberServer.domain.favorite.exception.FavoriteErrorCode;
import com.clubber.ClubberServer.global.error.BaseException;

public class ClubNotFoundException extends BaseException{
    public static final BaseException EXCEPTION = new ClubNotFoundException();

    public ClubNotFoundException() {
        super(ClubErrorCode.SEARCHED_CLUB_NOT_FOUND);
    }

}
