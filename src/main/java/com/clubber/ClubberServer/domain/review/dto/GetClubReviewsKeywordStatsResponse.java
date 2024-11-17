package com.clubber.ClubberServer.domain.review.dto;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.review.domain.Keyword;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubReviewsKeywordStatsResponse {
    @Schema(description = "동아리 id", example = "1")
    private final Long clubId;

    @Schema(description = "작성한 리뷰 키워드",
            example = "{\"😃 \" 분위기가 좋아요\": 10, "
                + "\"💵 \"회비가 적당해요\": 20, "
                + "🏻 \"활동 참여가 자유로워요\": 30, "
                + "🏆 \"대외활동에 좋아요\": 40, "
                + "🏻 \"운영진들이 일을 잘해요\": 50}")
    private final Map<String, Long> keywordStats;

    public static GetClubReviewsKeywordStatsResponse of (Club club, KeywordStats keywordStats){
        return GetClubReviewsKeywordStatsResponse.builder()
                .clubId(club.getId())
                .keywordStats(convertKeyType(keywordStats.getKeywordMap()))
                .build();
    }

    private static Map<String, Long> convertKeyType(Map<Keyword, Long> keywordStats){
        return keywordStats.entrySet().stream()
            .collect(Collectors.toMap(
                entry -> entry.getKey().getTitle(),
                entry -> entry.getValue(),
                (oldValue, newValue) -> oldValue,
                LinkedHashMap::new));
    }
}
