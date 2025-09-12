package com.clubber.ClubberServer.domain.club.exception;


import com.clubber.common.exception.BaseException;

public class ClubAlreadyDeletedException extends BaseException {

    public static final BaseException EXCEPTION = new ClubAlreadyDeletedException();
    private ClubAlreadyDeletedException() {
        super(ClubErrorCode.CLUB_ALREADY_DELETED);
    }
}
