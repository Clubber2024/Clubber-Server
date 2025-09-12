package com.clubber.ClubberServer.domain.review.exception;

import static com.clubber.ClubberServer.domain.review.exception.ReviewErrorCode.REVIEW_NOT_FOUND;

import com.clubber.common.exception.BaseException;

public class ReviewNotFoundException extends BaseException {

	public static final ReviewNotFoundException EXCEPTION = new ReviewNotFoundException();

	private ReviewNotFoundException() {
		super(REVIEW_NOT_FOUND);
	}
}
