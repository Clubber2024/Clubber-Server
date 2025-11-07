package com.clubber.ClubberServer.integration.util.fixture;

import java.util.List;

public class AdminReviewFixture {

	private static final List<Long> reviewIds = List.of(1L, 2L);
	private static final List<Long> notFoundReviewIds = List.of(3L, 4L);

	private static final List<Long> overMaxSizeReviews = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L);

	private static final List<Long> emptyReviewIds = List.of();
}
