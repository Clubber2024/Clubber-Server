package com.clubber.ClubberServer.domain.review.vo;

import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.domain.review.dto.KeywordCountStatDto;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class KeywordStatsVO {

	private final Map<Keyword, Long> keywordMap = new EnumMap<>(Keyword.class);

	public KeywordStatsVO(List<KeywordCountStatDto> keywordCountStatDtoList) {
		Arrays.stream(Keyword.values())
			.forEach(keyword -> keywordMap.put(keyword, 0L));
		updateKeywordStat(keywordCountStatDtoList);
	}

	private void updateKeywordStat(List<KeywordCountStatDto> keywordCountStatDtoList) {
		for (KeywordCountStatDto keywordCountStatDTO : keywordCountStatDtoList) {
			keywordMap.put(keywordCountStatDTO.keyword(), keywordCountStatDTO.count());
		}
	}
}
