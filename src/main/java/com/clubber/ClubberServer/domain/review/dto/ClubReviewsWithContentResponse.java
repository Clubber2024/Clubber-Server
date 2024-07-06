package com.clubber.ClubberServer.domain.review.dto;

import com.clubber.ClubberServer.domain.review.domain.ApprovedStatus;
import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.domain.ReviewKeyword;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ClubReviewsWithContentResponse {

    private final Long clubId;

    private final List<ClubReviewsWithContentDetailResponse> reviews;

    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class ClubReviewsWithContentDetailResponse {

        @Schema(description = "리뷰 id", example = "1")
        private final Long reviewId;

        @Schema(description = "유저 id", example = "1")
        private final Long userId;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
        @Schema(description = "리뷰 작성 일자", example = "2024.01.01", type = "string")
        private final LocalDateTime dateTime;

        @Schema(description = "작성한 리뷰 키워드",
                example = "[\"CULTURE\", \"FEE\", \"ACTIVITY\", \"CAREER\", \"MANAGE\"]")
        private final List<Keyword> keywords;

        private final String content;

        public static ClubReviewsWithContentDetailResponse of(Review review){
            return ClubReviewsWithContentDetailResponse.builder()
                    .keywords(ReviewKeyword.from(review.getReviewKeywords()))
                    .reviewId(review.getId())
                    .userId(review.getUser().getId())
                    .dateTime(review.getCreatedAt())
                    .content(review.getApprovedStatus() == ApprovedStatus.APPROVED ? review.getContent() : null)
                    .build();
        }
    }

    public static ClubReviewsWithContentResponse of(List<Review> reviews, Long clubId){
        return ClubReviewsWithContentResponse
                .builder()
                .clubId(clubId)
                .reviews(reviews.stream().map(ClubReviewsWithContentDetailResponse::of).collect(
                        Collectors.toList()))
                .build();
    }
}
