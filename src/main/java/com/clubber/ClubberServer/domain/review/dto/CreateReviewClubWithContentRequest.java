package com.clubber.ClubberServer.domain.review.dto;

import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.domain.ReviewKeyword;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateReviewClubWithContentRequest {

    @Schema(description = "작성하고자하는 한줄평", example = "활동이 재밌어요")
    private String content;

    @Size(min = 1, message = "1개 이상의 키워드를 선택해주세요")
    @Schema(description = "선택하려는 키워드")
    private Set<Keyword> keywords = EnumSet.noneOf(Keyword.class);
    
}
