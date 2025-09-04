package com.clubber.ClubberServer.integration.util.fixture;

import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsReviewApprovedStatusRequest;
import com.clubber.ClubberServer.domain.review.domain.ApprovedStatus;
import java.util.List;

public class AdminReviewFixture {

	private static final List<Long> reviewIds = List.of(1L, 2L);
	private static final List<Long> notFoundReviewIds = List.of(3L, 4L);

	private static final List<Long> overMaxSizeReviews = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L);

	private static final List<Long> emptyReviewIds = List.of();

	public static final UpdateAdminsReviewApprovedStatusRequest VALID_UPDATE_ADMINS_REVIEW_STATUS_REQEUST_APPROVE
		= new UpdateAdminsReviewApprovedStatusRequest(reviewIds, ApprovedStatus.APPROVED);

	public static final UpdateAdminsReviewApprovedStatusRequest VALID_UPDATE_ADMINS_REVIEW_STATUS_REQEUST_REJECT
		= new UpdateAdminsReviewApprovedStatusRequest(reviewIds, ApprovedStatus.REJECTED);

	public static final UpdateAdminsReviewApprovedStatusRequest UPDATE_ADMIN_NOT_FOUND_REVIEW_REQUEST
			= new UpdateAdminsReviewApprovedStatusRequest(notFoundReviewIds, ApprovedStatus.REJECTED);

	public static final UpdateAdminsReviewApprovedStatusRequest UPDATE_ADMINS_EMPTY_REVIEWS
			= new UpdateAdminsReviewApprovedStatusRequest(emptyReviewIds, ApprovedStatus.REJECTED);

	public static final UpdateAdminsReviewApprovedStatusRequest UPDATE_ADMINS_OVER_MAX_REVIEWS
			= new UpdateAdminsReviewApprovedStatusRequest(overMaxSizeReviews, ApprovedStatus.REJECTED);
}
