package com.clubber.ClubberServer.domain.admin.dto;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.review.domain.ApprovedStatus;
import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.domain.ReviewKeyword;
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
public class GetAdminsReviewsResponse {

    private final Long adminId;
    private final Long clubId;
    private final String clubName;
    private final List<GetAdminsReviewDetailsResponse> clubReviews;

    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static class GetAdminsReviewDetailsResponse{
        private final Long reviewId;
        private final ApprovedStatus approvedStatus;
        private final Set<Keyword> keywords;
        private final String content;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
        private final LocalDateTime dateTime;

        private static GetAdminsReviewDetailsResponse from(Review review){
            return GetAdminsReviewDetailsResponse.builder()
                    .reviewId(review.getId())
                    .approvedStatus(review.getApprovedStatus())
                    .keywords(ReviewKeyword.from(review.getReviewKeywords()))
                    .content(review.getContent())
                    .dateTime(review.getCreatedAt())
                    .build();
        }
    }

    public static GetAdminsReviewsResponse of(Admin admin, Club club, List<Review> reviews){
        return GetAdminsReviewsResponse.builder()
                .adminId(admin.getId())
                .clubId(club.getId())
                .clubName(club.getName())
                .clubReviews(reviews.stream().map(GetAdminsReviewDetailsResponse::from).collect(
                        Collectors.toList()))
                .build();
    }
}
