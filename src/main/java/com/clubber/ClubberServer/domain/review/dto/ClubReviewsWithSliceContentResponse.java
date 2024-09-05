package com.clubber.ClubberServer.domain.review.dto;

import com.clubber.ClubberServer.global.page.SliceResponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ClubReviewsWithSliceContentResponse {

	private final Long clubId;

	private final SliceResponse<ClubReviewsWithContentDetailResponse> reviews; 
}
