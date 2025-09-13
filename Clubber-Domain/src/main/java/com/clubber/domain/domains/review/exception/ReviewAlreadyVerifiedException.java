package com.clubber.domain.domains.review.exception;

import static com.clubber.domain.domains.review.exception.ReviewErrorCode.REVIEW_ALREADY_VERIFIED;

import com.clubber.common.exception.BaseException;

public class ReviewAlreadyVerifiedException extends BaseException {

	public static final BaseException EXCEPTION = new ReviewAlreadyVerifiedException();

	private ReviewAlreadyVerifiedException() {
		super(REVIEW_ALREADY_VERIFIED);
	}
}
