package com.clubber.ClubberServer.domain.review.dto;


import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.domain.ReviewKeyword;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.EnumSet;
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
public class ReviewCreateResponse {

    @Schema(description = "유저 id", example = "1")
    private final Long userId;

    @Schema(description = "동아리 id", example = "1")
    private final Long clubId;

    @Schema(description = "작성한 리뷰 키워드",
            example = "[\"CULTURE\", \"FEE\", \"ACTIVITY\", \"CAREER\", \"MANAGE\"]")
    private final Set<Keyword> keywords;

    public static ReviewCreateResponse of(Review review, List<ReviewKeyword> reviewkeywords){
        Set<Keyword> keywords = reviewkeywords.stream()
                .map(ReviewKeyword::getKeyword)
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(Keyword.class)));

        return ReviewCreateResponse.builder()
                .userId(review.getUser().getId())
                .clubId(review.getClub().getId())
                .keywords(keywords)
                .build();
    }
}
