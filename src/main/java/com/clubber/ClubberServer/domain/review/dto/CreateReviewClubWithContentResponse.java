package com.clubber.ClubberServer.domain.review.dto;

import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.domain.ReviewKeyword;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateReviewClubWithContentResponse {
    private final Long reviewId;
    private final String content;

    private final Set<Keyword> keywords;

    public static CreateReviewClubWithContentResponse of(Review review, List<ReviewKeyword> reviewKeywords) {
        return CreateReviewClubWithContentResponse.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .keywords(ReviewKeyword.from(reviewKeywords))
                .build();
    }
}
