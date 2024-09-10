package com.clubber.ClubberServer.domain.admin.dto;

import com.clubber.ClubberServer.global.page.SliceResponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetAdminPendingReviewsWithSliceResponse {

	private final Long lastReviewId; 

	private final SliceResponse<GetAdminsReviewByStatusResponse> reviews;
}
