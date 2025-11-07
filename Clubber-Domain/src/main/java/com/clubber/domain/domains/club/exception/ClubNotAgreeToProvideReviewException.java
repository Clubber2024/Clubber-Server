package com.clubber.domain.domains.club.exception;

import com.clubber.common.exception.BaseException;

public class ClubNotAgreeToProvideReviewException extends BaseException {
    public static final BaseException EXCEPTION = new ClubNotAgreeToProvideReviewException();
    private ClubNotAgreeToProvideReviewException() {
        super(ClubErrorCode.CLUB_NOT_AGREE_TO_PROVIDE_REVIEW);
    }
}
