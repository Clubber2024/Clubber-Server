package com.clubber.ClubberServer.domain.admin.dto;

import com.clubber.domain.domains.review.domain.ApprovedStatus;
import com.clubber.domain.domains.review.domain.Review;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminReviewResponse {

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

	public static AdminReviewResponse of(Review review, Set<String> keywords) {
		return AdminReviewResponse.builder()
			.reviewId(review.getId())
			.approvedStatus(review.getApprovedStatus())
			.keywords(keywords)
			.content(review.getContent())
			.dateTime(review.getCreatedAt())
			.build();
	}
}
