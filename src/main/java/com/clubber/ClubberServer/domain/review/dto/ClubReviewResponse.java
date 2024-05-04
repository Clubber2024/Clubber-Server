package com.clubber.ClubberServer.domain.review.dto;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.domain.ReviewKeyword;
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
public class ClubReviewResponse {
    private final Long clubId;

    private final List<ReviewResponse> reviews;

    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    private static class ReviewResponse{
        private final Long reviewId;
        private final Long userId;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
        private final LocalDateTime dateTime;

        private final List<Keyword> keywords;

        public static ReviewResponse of(Review review, List<Keyword> keywords){
            return ReviewResponse.builder()
                    .userId(review.getUser().getId())
                    .reviewId(review.getId())
                    .keywords(keywords)
                    .dateTime(review.getCreatedAt())
                    .build();
        }

    }

    public static ClubReviewResponse of(Club club, List<ReviewKeyword> reviewKeywords){

        Map<Review, List<Keyword>> reviewMap = getReviewListMap(reviewKeywords);
        List<ReviewResponse> reviews = getCollectReviewResponse(reviewMap);

        return ClubReviewResponse.builder()
                .clubId(club.getId())
                .reviews(reviews)
                .build();
    }

    private static List<ReviewResponse> getCollectReviewResponse(Map<Review, List<Keyword>> reviewMap) {
        return reviewMap.entrySet()
                .stream()
                .map(e -> ReviewResponse.of(e.getKey(), e.getValue())).
                collect(Collectors.toList());
    }

    private static Map<Review, List<Keyword>> getReviewListMap(List<ReviewKeyword> reviewKeywords) {
        Map<Review, List<Keyword>> reviewMap = reviewKeywords.stream()
                .collect(Collectors.groupingBy(ReviewKeyword::getReview,
                        Collectors.mapping(ReviewKeyword::getKeyword, Collectors.toList())));
        return reviewMap;
    }
}
