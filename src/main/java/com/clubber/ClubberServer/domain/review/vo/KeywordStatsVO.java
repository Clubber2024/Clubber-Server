package com.clubber.ClubberServer.domain.review.vo;

import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.domain.review.dto.KeywordCountStatDto;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;

public class KeywordStatsVO {

	//키워드 통계 반환 시 Key가 String이지만 추후 확장성을 위해 EnumMap으로 관리한다.
	private final Map<Keyword, Long> keywordMap = new EnumMap<>(Keyword.class);

	@Getter
	private final Map<String, Long> keywordMapAsStingKey;

	public KeywordStatsVO(List<KeywordCountStatDto> keywordCountStatDtoList) {
		initializeKeywordMap();
		updateKeywordStat(keywordCountStatDtoList);
		keywordMapAsStingKey = convertKeywordMapAsStringKey();
	}

	private void initializeKeywordMap() {
		Arrays.stream(Keyword.values())
			.forEach(keyword -> keywordMap.put(keyword, 0L));
	}

	private void updateKeywordStat(List<KeywordCountStatDto> keywordCountStatDtoList) {
		for (KeywordCountStatDto keywordCountStatDTO : keywordCountStatDtoList) {
			keywordMap.put(keywordCountStatDTO.keyword(), keywordCountStatDTO.count());
		}
	}

	private Map<String, Long> convertKeywordMapAsStringKey() {
		return keywordMap.entrySet().stream()
			.collect(Collectors.toMap(
				entry -> entry.getKey().getTitle(),
				Map.Entry::getValue,
				(oldValue, newValue) -> oldValue,
				LinkedHashMap::new)
			);
	}
}
