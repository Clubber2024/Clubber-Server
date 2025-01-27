package com.clubber.ClubberServer.integration.util.fixture;

import java.util.List;

import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.domain.review.dto.CreateClubReviewRequest;

public class ReviewFixture {

	public static Long exampleId = 100000000L;
	public static final CreateClubReviewRequest VALID_REVIEW_CREATE_REQUEST =
		new CreateClubReviewRequest("content", List.of(Keyword.CULTURE, Keyword.FEE), "image");

	public static final CreateClubReviewRequest EMPTY_KEYWORD_REVIEW_REQUEST =
		new CreateClubReviewRequest("content", List.of(), "image");

	public static final CreateClubReviewRequest LONG_SIZE_INVALID_REVIEW_REQUEST =
		new CreateClubReviewRequest("a".repeat(101), List.of(Keyword.CULTURE), "image");
}
