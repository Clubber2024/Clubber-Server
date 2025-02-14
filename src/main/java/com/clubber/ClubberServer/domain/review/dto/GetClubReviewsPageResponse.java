package com.clubber.ClubberServer.domain.review.dto;

import com.clubber.ClubberServer.global.common.page.PageResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubReviewsPageResponse {

	private final Long clubId;

	private final PageResponse<ClubReviewResponse> reviews;

	public static GetClubReviewsPageResponse of(PageResponse<ClubReviewResponse> reviews, Long clubId) {
		return GetClubReviewsPageResponse
			.builder()
			.clubId(clubId)
			.reviews(reviews)
			.build();
	}
}
