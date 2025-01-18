package com.clubber.ClubberServer.integration.util.fixture;

import java.util.EnumSet;
import java.util.List;

import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.domain.review.dto.CreateReviewClubWithContentRequest;

public class ReviewFixture {

	public static Long exampleId = 100000000L;
	public static final CreateReviewClubWithContentRequest VALID_REVIEW_CREATE_REQUEST =
		new CreateReviewClubWithContentRequest("content", List.of(Keyword.CULTURE, Keyword.FEE));

	public static final CreateReviewClubWithContentRequest EMPTY_KEYWORD_REVIEW_REQUEST =
		new CreateReviewClubWithContentRequest("content", List.of());

	public static final CreateReviewClubWithContentRequest LONG_SIZE_INVALID_REVIEW_REQUEST =
		new CreateReviewClubWithContentRequest("a".repeat(101), List.of(Keyword.CULTURE));
}
