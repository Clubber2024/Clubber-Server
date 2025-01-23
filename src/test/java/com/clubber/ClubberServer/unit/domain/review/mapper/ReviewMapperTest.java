package com.clubber.ClubberServer.unit.domain.review.mapper;

import static com.clubber.ClubberServer.domain.review.domain.Keyword.ACTIVITY;
import static com.clubber.ClubberServer.domain.review.domain.Keyword.CAREER;
import static com.clubber.ClubberServer.domain.review.domain.Keyword.CULTURE;
import static com.clubber.ClubberServer.domain.review.domain.Keyword.FEE;
import static com.clubber.ClubberServer.domain.review.domain.Keyword.MANAGE;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.domain.review.dto.GetClubReviewsKeywordStatsResponse;
import com.clubber.ClubberServer.domain.review.dto.KeywordCountStatDTO;
import com.clubber.ClubberServer.domain.review.vo.KeywordStatsVO;
import com.clubber.ClubberServer.domain.review.mapper.ReviewMapper;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReviewMapperTest {

	@InjectMocks
	private ReviewMapper reviewMapper;

	@Test
	void testKeywordMapping() {
		//given
		KeywordStatsVO keywordStatsVO = new KeywordStatsVO();
		keywordStatsVO.updateKeywordStat(getKeywordStats());

		Club club = getClub();

		//when
		GetClubReviewsKeywordStatsResponse getClubReviewsKeywordStatsResponse = reviewMapper.getGetClubReviewsKeywordStatsResponse(
			club, keywordStatsVO);

		//then
		Map<String, Long> keywordMap = getClubReviewsKeywordStatsResponse.getKeywordStats();
		Long clubId = getClubReviewsKeywordStatsResponse.getClubId();
		assertAll(
			() -> assertEquals(club.getId(), clubId),
			() -> assertEquals(10L, keywordMap.get(ACTIVITY.getTitle())),
			() -> assertEquals(20L, keywordMap.get(FEE.getTitle())),
			() -> assertEquals(30L, keywordMap.get(CAREER.getTitle())),
			() -> assertEquals(40L, keywordMap.get(CULTURE.getTitle())),
			() -> assertEquals(50L, keywordMap.get(MANAGE.getTitle()))
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

	private static Club getClub() {
		return Club.builder()
			.id(1L)
			.isAgreeToReview(true)
			.build();
	}
}
