package com.clubber.domain.domains.review.exception;

import static com.clubber.domain.domains.review.exception.ReviewErrorCode.REVIEW_NOT_FOUND;

import com.clubber.common.exception.BaseException;

public class ReviewNotFoundException extends BaseException {

	public static final ReviewNotFoundException EXCEPTION = new ReviewNotFoundException();

	private ReviewNotFoundException() {
		super(REVIEW_NOT_FOUND);
	}
}
