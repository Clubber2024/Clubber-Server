package com.clubber.ClubberServer.domain.review.dto;

import com.clubber.ClubberServer.domain.review.domain.Keyword;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewRequest {

    @Schema(description = "선택한 리뷰 키워드들",
            example = "{\"keywords\": [\"CULTURE\", \"FEE\"]}")
    private List<Keyword> keywords;
}
