package com.clubber.ClubberServer.domain.review.dto;

import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
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

    }
}
