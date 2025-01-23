package com.clubber.ClubberServer.unit.domain.review.service;

import static com.clubber.ClubberServer.domain.review.domain.Keyword.FEE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static reactor.core.publisher.Mono.when;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.domain.review.dto.GetClubReviewsKeywordStatsResponse;
import com.clubber.ClubberServer.domain.review.dto.KeywordStat;
import com.clubber.ClubberServer.domain.review.repository.ReviewKeywordRepository;
import com.clubber.ClubberServer.domain.review.service.ReviewService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

	@InjectMocks
	private ReviewService reviewService;

	@Mock
	private ReviewKeywordRepository reviewKeywordRepository;

	@Mock
	private ClubRepository clubRepository;

	@Test
	void keywordResponseTest() {
		//given
		List<KeywordStat> keywordStats = getKewordStats();
		Club club = getClub();

		doReturn(Optional.of(club))
			.when(clubRepository)
			.findClubByIdAndIsDeleted(any(Long.class), eq(false));

		doReturn(keywordStats)
			.when(reviewKeywordRepository)
			.queryReviewKeywordByClubId(any(Long.class));
		//when
		GetClubReviewsKeywordStatsResponse clubReviewKeywordStats = reviewService.getClubReviewKeywordStats(1L);

		//then
		Map<String, Long> keywordStatsResponse = clubReviewKeywordStats.getKeywordStats();
		assertEquals(keywordStatsResponse.get(FEE.getTitle()), 20L);
	}

	private static List<KeywordStat> getKewordStats() {
		return List.of(
			new KeywordStat(Keyword.ACTIVITY, 10L),
			new KeywordStat(FEE, 20L),
			new KeywordStat(Keyword.CAREER, 30L),
			new KeywordStat(Keyword.CULTURE, 40L),
			new KeywordStat(Keyword.MANAGE, 50L)
		);
	}

	private static Club getClub() {
		return Club.builder()
			.isAgreeToReview(true)
			.build();
	}
}
