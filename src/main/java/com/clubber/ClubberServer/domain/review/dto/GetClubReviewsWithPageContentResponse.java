package com.clubber.ClubberServer.domain.review.dto;

import org.springframework.data.domain.Page;

import com.clubber.ClubberServer.domain.review.domain.Review;
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

	public static GetClubReviewsWithPageContentResponse of(Page<Review> reviews, Long clubId) {
		return GetClubReviewsWithPageContentResponse
			.builder()
			.clubId(clubId)
			.reviews(PageResponse.of(reviews.map(ClubReviewsWithContentDetailResponse::of)))
			.build();
	}
}
