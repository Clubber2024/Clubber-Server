package com.clubber.ClubberServer.domain.review.dto;

import com.clubber.ClubberServer.domain.review.domain.Keyword;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

public class KeywordStats {
    private Map<Keyword, Long> keywordMap = new EnumMap<>(Keyword.class);

    public KeywordStats() {
        Arrays.stream(Keyword.values())
                .forEach(keyword -> keywordMap.put(keyword, 0L));
    }
}
