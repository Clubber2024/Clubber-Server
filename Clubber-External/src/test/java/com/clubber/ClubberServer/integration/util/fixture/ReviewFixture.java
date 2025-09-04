package com.clubber.ClubberServer.integration.util.fixture;

import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsPasswordRequest;
import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.domain.review.dto.CreateClubReviewRequest;
import com.navercorp.fixturemonkey.ArbitraryBuilder;

import java.util.List;

import static com.clubber.ClubberServer.integration.util.fixture.FixtureCommon.fixtureMonkey;

public class ReviewFixture {

	public static final CreateClubReviewRequest VALID_REVIEW_CREATE_REQUEST =
		new CreateClubReviewRequest("content", List.of(Keyword.CULTURE, Keyword.FEE), "image");

	public static final CreateClubReviewRequest EMPTY_KEYWORD_REVIEW_REQUEST =
		new CreateClubReviewRequest("content", List.of(), "image");

	public static final CreateClubReviewRequest LONG_SIZE_INVALID_REVIEW_REQUEST =
		new CreateClubReviewRequest("a".repeat(101), List.of(Keyword.CULTURE), "image");

	public static ArbitraryBuilder<CreateClubReviewRequest> getDefaultCreateClubReviewRequestBuilder() {
		return fixtureMonkey.giveMeBuilder(CreateClubReviewRequest.class)
				.set("content", "content")
				.set("keywords", List.of(Keyword.CULTURE, Keyword.FEE))
				.set("authImage", "authImage");
	}
}
