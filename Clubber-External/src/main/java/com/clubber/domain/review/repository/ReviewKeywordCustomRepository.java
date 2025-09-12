package com.clubber.domain.review.repository;

import com.clubber.domain.review.dto.KeywordCountStatDto;
import java.util.List;

public interface ReviewKeywordCustomRepository {

	List<KeywordCountStatDto> queryReviewKeywordStatsByClubId(Long clubId);

}
