package com.clubber.ClubberServer.domain.review.dto;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.review.domain.Keyword;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ClubReviewKeywordStatsResponse {
    @Schema(description = "동아리 id", example = "1")
    private final Long clubId;

    @Schema(description = "작성한 리뷰 키워드",
            example = "{\"CULTURE\": 10, \"FEE\": 20, \"ACTIVITY\": 30, \"CAREER\": 40, \"MANAGE\": 50}")
    private final Map<Keyword, Long> keywordStats;

    public static ClubReviewKeywordStatsResponse of (Club club, Map<Keyword, Long> keywordStats){
        return ClubReviewKeywordStatsResponse.builder()
                .clubId(club.getId())
                .keywordStats(keywordStats)
                .build();
    }
}
