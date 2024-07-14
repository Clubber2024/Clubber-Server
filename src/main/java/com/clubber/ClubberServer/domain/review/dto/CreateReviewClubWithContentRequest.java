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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateReviewClubWithContentRequest {

    @Schema(description = "작성하고자하는 한줄평", example = "활동이 재밌어요")
    private String content;

    @Schema(description = "선택하려는 키워드")
    private Set<Keyword> keywords = EnumSet.noneOf(Keyword.class);

    public List<ReviewKeyword> toEntity(Review review){
        return keywords.stream()
                .map((keyword) -> ReviewKeyword.of(review, keyword))
                .collect(Collectors.toList());
    }
}
