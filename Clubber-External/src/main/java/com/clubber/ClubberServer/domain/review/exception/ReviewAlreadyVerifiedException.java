package com.clubber.ClubberServer.domain.review.exception;

import static com.clubber.ClubberServer.domain.review.exception.ReviewErrorCode.REVIEW_ALREADY_VERIFIED;

import com.clubber.ClubberServer.global.exception.BaseException;

public class ReviewAlreadyVerifiedException extends BaseException {

	public static final BaseException EXCEPTION = new ReviewAlreadyVerifiedException();

	private ReviewAlreadyVerifiedException() {
		super(REVIEW_ALREADY_VERIFIED);
	}
}
