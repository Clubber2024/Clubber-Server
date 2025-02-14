package com.clubber.ClubberServer.unit.domain.review.vo;

import static com.clubber.ClubberServer.domain.review.domain.Keyword.ACTIVITY;
import static com.clubber.ClubberServer.domain.review.domain.Keyword.CAREER;
import static com.clubber.ClubberServer.domain.review.domain.Keyword.CULTURE;
import static com.clubber.ClubberServer.domain.review.domain.Keyword.FEE;
import static com.clubber.ClubberServer.domain.review.domain.Keyword.MANAGE;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.domain.review.dto.KeywordCountStatDto;
import com.clubber.ClubberServer.domain.review.vo.KeywordStatsVO;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReviewKeywordVOTest {


	@Test
	@DisplayName("keywordStat 리스트가 주어졌을 때, KeywordStatVO안의 Map에 매핑된다..")
	void keywordStatsTest() {
		//given & when
		KeywordStatsVO keywordStatsVO = new KeywordStatsVO(getKeywordStats());

		//then
		Map<String, Long> keywordMap = keywordStatsVO.getKeywordMapAsStingKey();
		assertAll(
			() -> assertEquals(10L, keywordMap.get(ACTIVITY.getTitle())),
			() -> assertEquals(20L, keywordMap.get(FEE.getTitle())),
			() -> assertEquals(30L, keywordMap.get(CAREER.getTitle())),
			() -> assertEquals(40L, keywordMap.get(CULTURE.getTitle())),
			() -> assertEquals(50L, keywordMap.get(MANAGE.getTitle()))
		);
	}

	private static List<KeywordCountStatDto> getKeywordStats() {
		return List.of(
			new KeywordCountStatDto(Keyword.ACTIVITY, 10L),
			new KeywordCountStatDto(FEE, 20L),
			new KeywordCountStatDto(Keyword.CAREER, 30L),
			new KeywordCountStatDto(Keyword.CULTURE, 40L),
			new KeywordCountStatDto(Keyword.MANAGE, 50L)
		);
	}
}
