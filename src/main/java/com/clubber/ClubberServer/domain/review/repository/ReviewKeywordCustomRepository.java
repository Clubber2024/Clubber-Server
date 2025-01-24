package com.clubber.ClubberServer.domain.review.repository;

import com.clubber.ClubberServer.domain.review.dto.KeywordCountStatDto;
import java.util.List;

public interface ReviewKeywordCustomRepository {

	List<KeywordCountStatDto> queryReviewKeywordStatsByClubId(Long clubId);

}
