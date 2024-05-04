package com.clubber.ClubberServer.domain.review.dto;

import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.domain.ReviewKeyword;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    private List<ReviewResponse> reviewResponses;

    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    public static class ReviewResponse {
        private Long reviewId;
        private List<Keyword> keywords;
        private Long clubId;
        private String clubName;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
        private LocalDateTime dateTime;

        private static ReviewResponse of(Review review, List<Keyword> keywords){
            return ReviewResponse.builder()
                    .reviewId(review.getId())
                    .keywords(keywords)
                    .clubId(review.getClub().getId())
                    .clubName(review.getClub().getName())
                    .dateTime(review.getCreatedAt()).build();
        }
    }

    public static UserReviewResponse of (User user, List<ReviewKeyword> keywords){
        Map<Review, List<Keyword>> reviewListMap = getReviewListMap(keywords);
        List<ReviewResponse> reviews = getCollectReviewResponse(reviewListMap);
        return UserReviewResponse.builder()
                .userId(user.getId())
                .reviewResponses(reviews).build();
    }

    private static List<ReviewResponse> getCollectReviewResponse(
            Map<Review, List<Keyword>> reviewListMap) {
        
        return reviewListMap.entrySet().stream()
                .map(e -> ReviewResponse.of(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    private static Map<Review, List<Keyword>> getReviewListMap(List<ReviewKeyword> keywords) {
        return keywords.stream()
                .collect(Collectors.groupingBy(ReviewKeyword::getReview,
                        Collectors.mapping(ReviewKeyword::getKeyword, Collectors.toList())));
    }
}
