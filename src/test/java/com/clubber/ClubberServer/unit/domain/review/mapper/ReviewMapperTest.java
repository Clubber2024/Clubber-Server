package com.clubber.ClubberServer.unit.domain.review.mapper;

import static com.clubber.ClubberServer.domain.review.domain.Keyword.ACTIVITY;
import static com.clubber.ClubberServer.domain.review.domain.Keyword.CAREER;
import static com.clubber.ClubberServer.domain.review.domain.Keyword.CULTURE;
import static com.clubber.ClubberServer.domain.review.domain.Keyword.FEE;
import static com.clubber.ClubberServer.domain.review.domain.Keyword.MANAGE;
import static org.assertj.core.api.Assertions.assertThat;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.review.dto.GetClubReviewsKeywordStatsResponse;
import com.clubber.ClubberServer.domain.review.dto.KeywordCountStatDto;
import com.clubber.ClubberServer.domain.review.mapper.ReviewMapper;
import com.clubber.ClubberServer.domain.review.vo.KeywordStatsVO;
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
		KeywordStatsVO keywordStatsVO = new KeywordStatsVO(getKeywordStats());
		Club club = getClub();

		//when
		GetClubReviewsKeywordStatsResponse getClubReviewsKeywordStatsResponse = reviewMapper.getGetClubReviewsKeywordStatsResponse(
			club, keywordStatsVO);

		//then
		Map<String, Long> keywordMap = getClubReviewsKeywordStatsResponse.getKeywordStats();

		assertThat(keywordMap)
			.containsExactlyInAnyOrderEntriesOf(Map.of(
				ACTIVITY.getTitle(), 10L,
				FEE.getTitle(), 20L,
				CAREER.getTitle(), 30L,
				CULTURE.getTitle(), 40L,
				MANAGE.getTitle(), 50L
			));
	}

	private static List<KeywordCountStatDto> getKeywordStats() {
		return List.of(
			new KeywordCountStatDto(ACTIVITY, 10L),
			new KeywordCountStatDto(FEE, 20L),
			new KeywordCountStatDto(CAREER, 30L),
			new KeywordCountStatDto(CULTURE, 40L),
			new KeywordCountStatDto(MANAGE, 50L)
		);
	}

	private static Club getClub() {
		return Club.builder()
			.id(1L)
			.isAgreeToReview(true)
			.build();
	}
}
