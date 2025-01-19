package com.clubber.ClubberServer.domain.review.dto.response;

import com.clubber.ClubberServer.global.common.page.PageResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubReviewsWithPageContentResponse {

	private final Long clubId;

	private final PageResponse<ClubReviewsWithContentDetailResponse> reviews;

	public static GetClubReviewsWithPageContentResponse of(PageResponse<ClubReviewsWithContentDetailResponse> reviews, Long clubId) {
		return GetClubReviewsWithPageContentResponse
			.builder()
			.clubId(clubId)
			.reviews(reviews)
			.build();
	}
}
