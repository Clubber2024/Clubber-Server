package com.clubber.ClubberServer.domain.review.dto;

import com.clubber.ClubberServer.domain.club.domain.Club;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubReviewAgreedStatusResponse {

	@Schema(name = "동아리 리뷰 동의 여부", example = "true")
	private boolean isAgreeToReview;

	public static GetClubReviewAgreedStatusResponse from(Club club){
		return GetClubReviewAgreedStatusResponse.builder()
			.isAgreeToReview(club.isAgreeToReview())
			.build();
	}
}
