package com.clubber.domain.domains.review.exception;

import com.clubber.common.exception.BaseException;

public class ReviewAlreadyHiddenException extends BaseException {

	public static final BaseException EXCEPTION = new ReviewAlreadyHiddenException();

	public ReviewAlreadyHiddenException() {
		super(ReviewErrorCode.REVIEW_ALREADY_HIDDEN);
	}
}

