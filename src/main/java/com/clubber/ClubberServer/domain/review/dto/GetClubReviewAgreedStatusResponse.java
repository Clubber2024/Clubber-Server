package com.clubber.ClubberServer.domain.review.dto;

import com.clubber.ClubberServer.domain.club.domain.Club;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubReviewAgreedStatusResponse {

	private boolean isAgreeToReview;

	public static GetClubReviewAgreedStatusResponse from(Club club){
		return GetClubReviewAgreedStatusResponse.builder()
			.isAgreeToReview(club.isAgreeToReview())
			.build();
	}
}
