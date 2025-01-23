package com.clubber.ClubberServer.domain.review.repository;

import com.clubber.ClubberServer.domain.review.dto.KeywordCountStatDTO;
import java.util.List;

public interface ReviewKeywordCustomRepository {

	List<KeywordCountStatDTO> queryReviewKeywordStatsByClubId(Long clubId);

}
