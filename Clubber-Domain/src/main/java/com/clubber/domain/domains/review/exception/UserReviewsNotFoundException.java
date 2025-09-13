package com.clubber.domain.domains.review.exception;

import com.clubber.common.exception.BaseException;

public class UserReviewsNotFoundException extends BaseException {

	public static final BaseException EXCEPTION = new UserReviewsNotFoundException();

	private UserReviewsNotFoundException() {
		super(ReviewErrorCode.USER_REVIEWS_NOT_FOUND);
	}
}
