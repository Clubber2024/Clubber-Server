package com.clubber.ClubberServer.unit.domain.review.vo;

import static com.clubber.ClubberServer.domain.review.domain.Keyword.ACTIVITY;
import static com.clubber.ClubberServer.domain.review.domain.Keyword.CAREER;
import static com.clubber.ClubberServer.domain.review.domain.Keyword.CULTURE;
import static com.clubber.ClubberServer.domain.review.domain.Keyword.FEE;
import static com.clubber.ClubberServer.domain.review.domain.Keyword.MANAGE;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.domain.review.dto.KeywordCountStatDTO;
import com.clubber.ClubberServer.domain.review.vo.KeywordStatsVO;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReviewKeywordVOTest {


	@Test
	@DisplayName("keywordStat 리스트가 주어졌을 때, KeywordStatVO안의 Map에 매핑된다..")
	void keywordStatsTest() {
		//given
		KeywordStatsVO keywordStatsVO = new KeywordStatsVO();
		List<KeywordCountStatDTO> keywordStatList = getKeywordStats();

		//when
		keywordStatsVO.updateKeywordStat(keywordStatList);

		//then
		Map<Keyword, Long> keywordMap = keywordStatsVO.getKeywordMap();
		assertAll(
			() -> assertEquals(10L, keywordMap.get(ACTIVITY)),
			() -> assertEquals(20L, keywordMap.get(FEE)),
			() -> assertEquals(30L, keywordMap.get(CAREER)),
			() -> assertEquals(40L, keywordMap.get(CULTURE)),
			() -> assertEquals(50L, keywordMap.get(MANAGE))
		);
	}

	private static List<KeywordCountStatDTO> getKeywordStats() {
		return List.of(
			new KeywordCountStatDTO(Keyword.ACTIVITY, 10L),
			new KeywordCountStatDTO(FEE, 20L),
			new KeywordCountStatDTO(Keyword.CAREER, 30L),
			new KeywordCountStatDTO(Keyword.CULTURE, 40L),
			new KeywordCountStatDTO(Keyword.MANAGE, 50L)
		);
	}
}
