package com.clubber.ClubberServer.domain.club.exception;

import com.clubber.ClubberServer.global.exception.BaseException;

public class ClubIdNotFoundException extends BaseException {
    public static final BaseException EXCEPTION = new ClubIdNotFoundException();

    private ClubIdNotFoundException() {
        super(ClubErrorCode.CLUBID_NOT_FOUND);
    }
}
