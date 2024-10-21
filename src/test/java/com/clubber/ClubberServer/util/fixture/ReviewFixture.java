package com.clubber.ClubberServer.util.fixture;

import java.util.EnumSet;

import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.domain.review.dto.CreateReviewClubWithContentRequest;

public class ReviewFixture {
	public static final CreateReviewClubWithContentRequest VALID_REVIEW_CREATE_REQUEST =
		new CreateReviewClubWithContentRequest("content", EnumSet.of(Keyword.CULTURE, Keyword.FEE));
}
