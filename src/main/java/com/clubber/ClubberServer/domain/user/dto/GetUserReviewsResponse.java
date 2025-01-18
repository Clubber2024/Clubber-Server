package com.clubber.ClubberServer.domain.user.dto;

import com.clubber.ClubberServer.domain.review.domain.ApprovedStatus;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.domain.ReviewKeyword;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetUserReviewsResponse {
    private final Long userId;

    private final List<UserReviewDetailResponse> userReviews;

    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class UserReviewDetailResponse {

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
        private final ApprovedStatus approvedStatus;

        @Schema(description = "리뷰 작성 시 선택한 키워드",
                example = "[\"CULTURE\", \"FEE\", \"ACTIVITY\", \"CAREER\", \"MANAGE\"]")
        private final Set<String> keywords;

        @Schema(description = "한줄평", example = "매주 정기회의가 있어서 시간 투자가 필요합니다!")
        private final String content;

        private static UserReviewDetailResponse of(Review review){
            return UserReviewDetailResponse.builder()
                    .reviewId(review.getId())
                    .keywords(ReviewKeyword.from(review.getReviewKeywords()))
                    .clubId(review.getClub().getId())
                    .clubName(review.getClub().getName())
                    .dateTime(review.getCreatedAt())
                    .approvedStatus(review.getApprovedStatus())
                    .content(review.getContent())
                    .build();
        }
    }

    public static GetUserReviewsResponse of(User user, List<Review> reviews){
        List<UserReviewDetailResponse> reviewDetails = reviews.stream().map(UserReviewDetailResponse::of)
                .collect(Collectors.toList());
        return GetUserReviewsResponse.builder()
                .userId(user.getId())
                .userReviews(reviewDetails)
                .build();
    }
}
