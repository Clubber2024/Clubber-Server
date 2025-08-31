package com.clubber.ClubberServer.domain.admin.dto;

import com.clubber.ClubberServer.global.common.slice.SliceResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetAdminPendingReviewsSliceResponse {

	private final Long lastReviewId; 

	private final SliceResponse<GetAdminsPendingReviews> reviews;

	public static GetAdminPendingReviewsSliceResponse of(SliceResponse<GetAdminsPendingReviews> reviews, Long lastReviewId){
		return GetAdminPendingReviewsSliceResponse.builder()
			.lastReviewId(lastReviewId)
			.reviews(reviews)
			.build();
	}
}
