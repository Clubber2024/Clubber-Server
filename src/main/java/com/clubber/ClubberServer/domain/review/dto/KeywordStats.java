package com.clubber.ClubberServer.domain.review.dto;

import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.domain.review.dto.KeywordStat;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class KeywordStats {
    private Map<Keyword, Long> keywordMap = new EnumMap<>(Keyword.class);

    public KeywordStats() {
        Arrays.stream(Keyword.values())
                .forEach(keyword -> keywordMap.put(keyword, 0L));
    }

    public void updateKeywordStat(List<KeywordStat> keywordStatList) {
        for (KeywordStat keywordStat : keywordStatList) {
            keywordMap.put(keywordStat.getKeyword(), keywordStat.getCount());
        }
    }
}
