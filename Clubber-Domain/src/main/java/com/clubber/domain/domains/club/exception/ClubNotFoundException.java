package com.clubber.domain.domains.club.exception;

import com.clubber.common.exception.BaseException;

public class ClubNotFoundException extends BaseException{
    public static final BaseException EXCEPTION = new ClubNotFoundException();

    private ClubNotFoundException() {
        super(ClubErrorCode.SEARCHED_CLUB_NOT_FOUND);
    }

}
