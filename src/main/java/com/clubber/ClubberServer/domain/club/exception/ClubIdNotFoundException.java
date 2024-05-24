package com.clubber.ClubberServer.domain.club.exception;

import com.clubber.ClubberServer.global.error.BaseException;

public class ClubIdNotFoundException extends BaseException {
    public static final BaseException EXCEPTION = new ClubIdNotFoundException();

    public ClubIdNotFoundException() {
        super(ClubErrorCode.CLUBID_NOT_FOUND);
    }
}
