package com.clubber.ClubberServer.domain.club.exception;

import com.clubber.ClubberServer.global.error.BaseException;

public class ClubNotAgreeToProvideInfoException extends BaseException{
    public static final BaseException EXCEPTION = new ClubNotAgreeToProvideInfoException();
    private ClubNotAgreeToProvideInfoException() {
        super(ClubErrorCode.CLUB_NOT_AGREED_TO_PROVIDE_INFO);
    }
}
