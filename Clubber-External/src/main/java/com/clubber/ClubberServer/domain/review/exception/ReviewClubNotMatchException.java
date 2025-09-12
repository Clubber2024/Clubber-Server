package com.clubber.ClubberServer.domain.review.exception;

import com.clubber.common.exception.BaseException;

public class ReviewClubNotMatchException extends BaseException {

	public static final BaseException EXCEPTION = new ReviewClubNotMatchException();

	private ReviewClubNotMatchException() {
		super(ReviewErrorCode.REVIEW_CLUB_NOT_MATCHED);
	}
}
