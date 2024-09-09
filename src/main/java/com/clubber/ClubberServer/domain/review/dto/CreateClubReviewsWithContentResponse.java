package com.clubber.ClubberServer.domain.review.dto;

import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.domain.ReviewKeyword;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateClubReviewsWithContentResponse {

    @Schema(description = "작성된 리뷰 id", example = "1")
    private final Long reviewId;

    @Schema(description = "작성된 한줄평", example = "활동이 재밌어요")
    private final String content;

    private final Set<String> keywords;

    public static CreateClubReviewsWithContentResponse of(Review review, List<ReviewKeyword> reviewKeywords) {
        return CreateClubReviewsWithContentResponse.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .keywords(ReviewKeyword.from(reviewKeywords))
                .build();
    }
}
