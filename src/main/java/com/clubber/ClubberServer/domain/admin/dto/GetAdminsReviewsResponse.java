package com.clubber.ClubberServer.domain.admin.dto;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.global.common.page.PageResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetAdminsReviewsResponse {

	@Schema(description = "동아리 계정 id", example = "1")
	private final Long adminId;

	@Schema(description = "동아리 id", example = "1")
	private final Long clubId;

	@Schema(description = "동아리 이름", example = "club1")
	private final String clubName;

	@Schema(description = "리뷰 목록")
	private final PageResponse<AdminReviewResponse> clubReviews;

	public static GetAdminsReviewsResponse of(Admin admin, Club club, PageResponse<AdminReviewResponse> clubReviews) {
		return GetAdminsReviewsResponse.builder()
			.adminId(admin.getId())
			.clubId(club.getId())
			.clubName(club.getName())
			.clubReviews(clubReviews)
			.build();
	}
}
