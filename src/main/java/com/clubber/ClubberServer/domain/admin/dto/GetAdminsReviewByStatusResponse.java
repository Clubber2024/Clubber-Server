package com.clubber.ClubberServer.domain.admin.dto;

import com.clubber.ClubberServer.domain.review.domain.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetAdminsReviewByStatusResponse {

    @Schema(name = "리뷰 id", example = "1")
    private final Long reviewId;

    @Schema(name = "한줄평", example = "분위기가 좋아요")
    private final String content;

    public static List<GetAdminsReviewByStatusResponse> from(List<Review> reviews){
        return reviews.stream()
                .map((r) ->GetAdminsReviewByStatusResponse.from(r))
                .collect(Collectors.toList());

    }

    private static GetAdminsReviewByStatusResponse from(Review review){
        return GetAdminsReviewByStatusResponse.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .build();
    }
}
