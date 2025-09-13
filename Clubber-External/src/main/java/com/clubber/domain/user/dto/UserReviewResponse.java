package com.clubber.domain.user.dto;

import com.clubber.domain.domains.review.domain.DeletionStatus;
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
public class UserReviewResponse {

	@Schema(description = "리뷰 id", example = "1")
	private final Long reviewId;

	@Schema(description = "동아리 id", example = "1")
	private final Long clubId;

	@Schema(description = "동아리 이름", example = "1")
	private final String clubName;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
	@Schema(description = "리뷰 작성 일자", example = "2024.01.01", type = "string")
	private final LocalDateTime dateTime;

	@Schema(description = "리뷰 승인 상태", example = "APPROVED")
	private final DeletionStatus deletionStatus;

	@Schema(description = "리뷰 작성 시 선택한 키워드",
		example = "[\"CULTURE\", \"FEE\", \"ACTIVITY\", \"CAREER\", \"MANAGE\"]")
	private final Set<String> keywords;

	@Schema(description = "한줄평", example = "매주 정기회의가 있어서 시간 투자가 필요합니다!")
	private final String content;

	public static UserReviewResponse of(Review review, Set<String> keywords) {
		return UserReviewResponse.builder()
			.reviewId(review.getId())
			.keywords(keywords)
			.clubId(review.getClub().getId())
			.clubName(review.getClub().getName())
			.dateTime(review.getCreatedAt())
			.deletionStatus(review.getDeletionStatus())
			.content(review.getContent())
			.build();
	}
}
