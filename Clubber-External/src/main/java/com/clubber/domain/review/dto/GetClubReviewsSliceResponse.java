package com.clubber.domain.review.dto;

import com.clubber.global.common.slice.SliceResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubReviewsSliceResponse {

	private final Long clubId;

	private final Long lastReviewId;

	private final SliceResponse<ClubReviewResponse> reviews;

	public static GetClubReviewsSliceResponse of(Long clubId, Long lastReviewId, SliceResponse<ClubReviewResponse> reviews){
		return GetClubReviewsSliceResponse.builder()
			.clubId(clubId)
			.lastReviewId(lastReviewId)
			.reviews(reviews)
			.build();
	}
}
