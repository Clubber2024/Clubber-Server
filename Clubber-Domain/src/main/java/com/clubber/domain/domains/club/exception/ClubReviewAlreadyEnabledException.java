package com.clubber.domain.domains.club.exception;

import com.clubber.common.exception.BaseException;

public class ClubReviewAlreadyEnabledException extends BaseException {
    public static final BaseException EXCEPTION = new ClubReviewAlreadyEnabledException();

    private ClubReviewAlreadyEnabledException() {
        super(ClubErrorCode.CLUB_REVIEW_ALREADY_ENABLED);
    }
}
