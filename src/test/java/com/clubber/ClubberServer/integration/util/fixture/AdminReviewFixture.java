package com.clubber.ClubberServer.integration.util.fixture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsReviewStatusRequest;
import com.clubber.ClubberServer.domain.review.domain.ApprovedStatus;

public class AdminReviewFixture {

	private static final List<Long> reviewIds = Arrays.asList(100000000L);
	private static final List<Long> notFoundReviewIds = List.of(10000001L);

	private static final List<Long> overMaxSizeReviews = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L);

	private static final List<Long> emptyReviewIds = List.of();

	public static final UpdateAdminsReviewStatusRequest VALID_UPDATE_ADMINS_REVIEW_STATUS_REQEUST_APPROVE
		= new UpdateAdminsReviewStatusRequest(reviewIds, ApprovedStatus.APPROVED);

	public static final UpdateAdminsReviewStatusRequest VALID_UPDATE_ADMINS_REVIEW_STATUS_REQEUST_REJECT
		= new UpdateAdminsReviewStatusRequest(reviewIds, ApprovedStatus.REJECTED);

	public static final UpdateAdminsReviewStatusRequest UPDATE_ADMIN_NOT_FOUND_REVIEW_REQUEST
			= new UpdateAdminsReviewStatusRequest(notFoundReviewIds, ApprovedStatus.REJECTED);

	public static final UpdateAdminsReviewStatusRequest UPDATE_ADMINS_EMPTY_REVIEWS
			= new UpdateAdminsReviewStatusRequest(emptyReviewIds, ApprovedStatus.REJECTED);

	public static final UpdateAdminsReviewStatusRequest UPDATE_ADMINS_OVER_MAX_REVIEWS
			= new UpdateAdminsReviewStatusRequest(overMaxSizeReviews, ApprovedStatus.REJECTED);
}
