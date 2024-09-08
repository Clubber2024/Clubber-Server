package com.clubber.ClubberServer.domain.club.exception;

import com.clubber.ClubberServer.global.error.BaseException;

public class ClubNotAgreeToProvideReviewException extends BaseException {
    public static final BaseException EXCEPTION = new ClubNotAgreeToProvideReviewException();
    private ClubNotAgreeToProvideReviewException() {
        super(ClubErrorCode.CLUB_NOT_AGREE_TO_PROVIDE_REVIEW);
    }
}
