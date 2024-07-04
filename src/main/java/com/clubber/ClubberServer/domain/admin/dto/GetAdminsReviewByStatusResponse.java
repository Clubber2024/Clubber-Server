package com.clubber.ClubberServer.domain.admin.dto;

import com.clubber.ClubberServer.domain.review.domain.Review;
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
    private final String content;
    private final Long reviewId;

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
