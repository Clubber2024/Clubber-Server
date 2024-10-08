package com.clubber.ClubberServer.domain.review.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;

import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.global.page.SliceResponse;
import com.clubber.ClubberServer.global.page.SliceUtil;

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

	public static GetClubReviewsWithSliceContentResponse of(List<Review> reviews, Long clubId, Pageable pageable){
		return GetClubReviewsWithSliceContentResponse.builder()
			.clubId(clubId)
			.lastReviewId(SliceUtil.hasNext(reviews, pageable) ?
				SliceUtil.getLastContent(reviews).getId() : null)
			.reviews(SliceUtil.valueOf(from(reviews), pageable))
			.build();
	}

	private static List<ClubReviewsWithContentDetailResponse> from(List<Review> reviews){
		return reviews.stream()
			.map(ClubReviewsWithContentDetailResponse::of)
			.collect(Collectors.toList());
	}
}
