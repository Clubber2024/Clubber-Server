package com.clubber.ClubberServer.domain.review.exception;

import com.clubber.common.exception.BaseException;

public class ReviewEnumNotMatchedException extends BaseException {

	public static final BaseException EXCEPTION = new ReviewEnumNotMatchedException();

	private ReviewEnumNotMatchedException() {
		super(ReviewErrorCode.REVIEW_KEYWORD_ENUM_NOT_MATCHED);
	}
}
