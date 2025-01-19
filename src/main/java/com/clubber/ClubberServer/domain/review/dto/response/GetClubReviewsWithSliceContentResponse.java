package com.clubber.ClubberServer.domain.review.dto.response;

import com.clubber.ClubberServer.global.common.slice.SliceResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubReviewsWithSliceContentResponse {

	private final Long clubId;

	private final Long lastReviewId;

	private final SliceResponse<ClubReviewsWithContentDetailResponse> reviews;

	public static GetClubReviewsWithSliceContentResponse of(Long clubId, Long lastReviewId, SliceResponse<ClubReviewsWithContentDetailResponse> reviews){
		return GetClubReviewsWithSliceContentResponse.builder()
			.clubId(clubId)
			.lastReviewId(lastReviewId)
			.reviews(reviews)
			.build();
	}
}
