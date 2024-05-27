package com.clubber.ClubberServer.domain.review.dto;

import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.domain.ReviewKeyword;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class UserReviewResponse {
    private Long userId;

    private List<UserReviewDetailResponse> userReviews;

    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    public static class UserReviewDetailResponse {

        @Schema(description = "리뷰 id", example = "1")
        private Long reviewId;

        @Schema(description = "동아리 id", example = "1")
        private Long clubId;

        @Schema(description = "동아리 이름", example = "1")
        private String clubName;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
        @Schema(description = "리뷰 작성 일자", example = "2024.01.01", type = "string")
        private LocalDateTime dateTime;

        @Schema(description = "리뷰 작성 시 선택한 키워드",
                example = "[\"CULTURE\", \"FEE\", \"ACTIVITY\", \"CAREER\", \"MANAGE\"]")
        private List<Keyword> keywords;

        private static UserReviewDetailResponse of(Review review, List<Keyword> keywords){
            return UserReviewDetailResponse.builder()
                    .reviewId(review.getId())
                    .keywords(keywords)
                    .clubId(review.getClub().getId())
                    .clubName(review.getClub().getName())
                    .dateTime(review.getCreatedAt()).build();
        }
    }

    public static UserReviewResponse of (User user, List<ReviewKeyword> keywords){
        Map<Review, List<Keyword>> reviewListMap = getReviewListMap(keywords);
        List<UserReviewDetailResponse> reviews = getCollectUserReviewDetailResponse(reviewListMap);
        return UserReviewResponse.builder()
                .userId(user.getId())
                .userReviews(reviews).build();
    }

    private static List<UserReviewDetailResponse> getCollectUserReviewDetailResponse(
            Map<Review, List<Keyword>> reviewListMap) {
        
        return reviewListMap.entrySet().stream()
                .map(e -> UserReviewDetailResponse.of(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    private static Map<Review, List<Keyword>> getReviewListMap(List<ReviewKeyword> keywords) {
        return keywords.stream()
                .collect(Collectors.groupingBy(ReviewKeyword::getReview,
                        Collectors.mapping(ReviewKeyword::getKeyword, Collectors.toList())));
    }
}
