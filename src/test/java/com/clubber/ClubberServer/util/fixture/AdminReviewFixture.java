package com.clubber.ClubberServer.util.fixture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsReviewStatusRequest;
import com.clubber.ClubberServer.domain.review.domain.ApprovedStatus;

public class AdminReviewFixture {

	private static final List<Long> reviewIds = Arrays.asList(100000000L);

	public static final UpdateAdminsReviewStatusRequest VALID_UPDATE_ADMINS_REVIEW_STATUS_REQEUST_APPROVE
		= new UpdateAdminsReviewStatusRequest(reviewIds, ApprovedStatus.APPROVED);

	public static final UpdateAdminsReviewStatusRequest VALID_UPDATE_ADMINS_REVIEW_STATUS_REQEUST_REJECT
		= new UpdateAdminsReviewStatusRequest(reviewIds, ApprovedStatus.REJECTED);
}
