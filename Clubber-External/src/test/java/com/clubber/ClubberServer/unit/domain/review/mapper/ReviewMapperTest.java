package com.clubber.ClubberServer.unit.domain.review.mapper;

import com.clubber.domain.review.mapper.ReviewMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReviewMapperTest {

	@InjectMocks
	private ReviewMapper reviewMapper;

//	@Test
//	void testKeywordMapping() {
//		//given
//		KeywordStatsVO keywordStatsVO = new KeywordStatsVO(getKeywordStats());
//		Club club = getClub();
//
//		//when
//		GetClubReviewsKeywordStatsResponse getClubReviewsKeywordStatsResponse = reviewMapper.getGetClubReviewsKeywordStatsResponse(
//			club, keywordStatsVO);
//
//		//then
//		Map<String, Long> keywordMap = getClubReviewsKeywordStatsResponse.getKeywordStats();
//
//		assertThat(keywordMap)
//			.containsExactlyInAnyOrderEntriesOf(Map.of(
//				ACTIVITY.getTitle(), 10L,
//				FEE.getTitle(), 20L,
//				CAREER.getTitle(), 30L,
//				CULTURE.getTitle(), 40L,
//				MANAGE.getTitle(), 50L
//			));
//	}
//
//	private static List<KeywordCountStatDto> getKeywordStats() {
//		return List.of(
//			new KeywordCountStatDto(ACTIVITY, 10L),
//			new KeywordCountStatDto(FEE, 20L),
//			new KeywordCountStatDto(CAREER, 30L),
//			new KeywordCountStatDto(CULTURE, 40L),
//			new KeywordCountStatDto(MANAGE, 50L)
//		);
//	}
//
//	private static Club getClub() {
//		return Club.builder()
//			.id(1L)
//			.isAgreeToReview(true)
//			.build();
//	}
}
