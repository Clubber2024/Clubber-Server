package com.clubber.ClubberServer.domain.review.dto;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.domain.ReviewKeyword;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ClubReviewResponse {

    @Schema(description = "동아리 id", example = "1")
    private final Long clubId;

    @Schema(description = "리뷰 목록")
    private final List<ClubReviewDetailResponse> clubReviews;

    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class ClubReviewDetailResponse{

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

        public static ClubReviewDetailResponse of(Review review, List<Keyword> keywords){
            return ClubReviewDetailResponse.builder()
                    .userId(review.getUser().getId())
                    .reviewId(review.getId())
                    .keywords(keywords)
                    .dateTime(review.getCreatedAt())
                    .build();
        }

    }

    public static ClubReviewResponse of(Club club, List<ReviewKeyword> reviewKeywords){

        Map<Review, List<Keyword>> reviewMap = getReviewListMap(reviewKeywords);
        List<ClubReviewDetailResponse> reviews = getCollectClubReviewDetailResponse(reviewMap);

        return ClubReviewResponse.builder()
                .clubId(club.getId())
                .clubReviews(reviews)
                .build();
    }

    private static List<ClubReviewDetailResponse> getCollectClubReviewDetailResponse(Map<Review, List<Keyword>> reviewMap) {
        return reviewMap.entrySet()
                .stream()
                .map(e -> ClubReviewDetailResponse.of(e.getKey(), e.getValue())).
                collect(Collectors.toList());
    }

    private static Map<Review, List<Keyword>> getReviewListMap(List<ReviewKeyword> reviewKeywords) {
        Map<Review, List<Keyword>> reviewMap = reviewKeywords.stream()
                .collect(Collectors.groupingBy(ReviewKeyword::getReview,
                        Collectors.mapping(ReviewKeyword::getKeyword, Collectors.toList())));
        return reviewMap;
    }
}
