package com.clubber.ClubberServer.domain.review.dto;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.review.domain.Keyword;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ClubReviewKeywordStatsResponse {
    private final Long clubId;

    private final Map<Keyword, Long> keywordStats;

    public static ClubReviewKeywordStatsResponse of (Club club, Map<Keyword, Long> keywordStats){
        return ClubReviewKeywordStatsResponse.builder()
                .clubId(club.getId())
                .keywordStats(keywordStats)
                .build();
    }
}
