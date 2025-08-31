package com.clubber.ClubberServer.domain.club.exception;

import com.clubber.ClubberServer.global.exception.BaseException;

public class ClubNotFoundException extends BaseException{
    public static final BaseException EXCEPTION = new ClubNotFoundException();

    private ClubNotFoundException() {
        super(ClubErrorCode.SEARCHED_CLUB_NOT_FOUND);
    }

}
