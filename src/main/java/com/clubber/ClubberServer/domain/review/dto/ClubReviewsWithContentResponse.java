package com.clubber.ClubberServer.domain.review.dto;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.clubber.ClubberServer.domain.review.domain.ApprovedStatus;
import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.domain.ReviewKeyword;
import com.clubber.ClubberServer.global.page.PageResponse;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ClubReviewsWithContentResponse {

	private final Long clubId;

	private final PageResponse<ClubReviewsWithContentDetailResponse> reviews;

	public static ClubReviewsWithContentResponse of(Page<Review> reviews, Long clubId) {
		return ClubReviewsWithContentResponse
			.builder()
			.clubId(clubId)
			.reviews(PageResponse.of(reviews.map(ClubReviewsWithContentDetailResponse::of)))
			.build();
	}
}
