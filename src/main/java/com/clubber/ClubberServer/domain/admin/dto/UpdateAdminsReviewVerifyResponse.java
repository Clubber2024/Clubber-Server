package com.clubber.ClubberServer.domain.admin.dto;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.review.domain.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record UpdateAdminsReviewVerifyResponse(
	@Schema(description = "리뷰 id") Long reviewId,
	@Schema(description = "관리자 id") Long adminId
) {

	public static UpdateAdminsReviewVerifyResponse of(Review review, Admin admin) {
		return UpdateAdminsReviewVerifyResponse.builder()
			.reviewId(review.getId())
			.adminId(admin.getId())
			.build();
	}
}
