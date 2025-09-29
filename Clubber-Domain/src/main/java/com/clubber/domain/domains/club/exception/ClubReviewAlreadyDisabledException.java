package com.clubber.domain.domains.club.exception;

import com.clubber.common.exception.BaseException;

public class ClubReviewAlreadyDisabledException extends BaseException {
    public static BaseException EXCEPTION = new ClubReviewAlreadyDisabledException();

    private ClubReviewAlreadyDisabledException() {
        super(ClubErrorCode.CLUB_REVIEW_ALREADY_DISABLED);
    }
}
