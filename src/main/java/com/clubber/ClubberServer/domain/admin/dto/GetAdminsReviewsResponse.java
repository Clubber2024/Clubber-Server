package com.clubber.ClubberServer.domain.admin.dto;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.club.domain.Club;
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
public class GetAdminsReviewsResponse {

	@Schema(description = "동아리 계정 id", example = "1")
	private final Long adminId;

	@Schema(description = "동아리 id", example = "1")
	private final Long clubId;

	@Schema(description = "동아리 이름", example = "club1")
	private final String clubName;

	@Schema(description = "리뷰 목록")
	private final PageResponse<GetAdminsReviewDetailsResponse> clubReviews;

	@Getter
	@Builder(access = AccessLevel.PRIVATE)
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	private static class GetAdminsReviewDetailsResponse {

		@Schema(description = "리뷰 id", example = "1")
		private final Long reviewId;

		@Schema(description = "리뷰 상태", example = "APPROVED")
		private final ApprovedStatus approvedStatus;

		@Schema(description = "작성한 리뷰 키워드",
			example = "[\"CULTURE\", \"FEE\", \"ACTIVITY\", \"CAREER\", \"MANAGE\"]")
		private final Set<String> keywords;

		@Schema(description = "리뷰 한줄평", example = "분위기가 좋아요")
		private final String content;

		@Schema(description = "리뷰 작성 일자", example = "2024.01.01", type = "string")
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
		private final LocalDateTime dateTime;

		private static GetAdminsReviewDetailsResponse from(Review review) {
			return GetAdminsReviewDetailsResponse.builder()
				.reviewId(review.getId())
				.approvedStatus(review.getApprovedStatus())
				.keywords(ReviewKeyword.from(review.getReviewKeywords()))
				.content(review.getContent())
				.dateTime(review.getCreatedAt())
				.build();
		}
	}

	public static GetAdminsReviewsResponse of(Admin admin, Club club, Page<Review> reviews) {
		return GetAdminsReviewsResponse.builder()
			.adminId(admin.getId())
			.clubId(club.getId())
			.clubName(club.getName())
			.clubReviews(PageResponse.of(reviews.map(GetAdminsReviewDetailsResponse::from)))
			.build();
	}
}
