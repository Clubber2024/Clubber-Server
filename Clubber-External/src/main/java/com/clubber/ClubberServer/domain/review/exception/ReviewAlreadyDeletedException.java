package com.clubber.ClubberServer.domain.review.exception;

import com.clubber.common.exception.BaseException;

public class ReviewAlreadyDeletedException extends BaseException {

	public static final BaseException EXCEPTION = new ReviewAlreadyDeletedException();

	public ReviewAlreadyDeletedException() {
		super(ReviewErrorCode.REVIEW_ALREADY_DELETED);
	}
}
