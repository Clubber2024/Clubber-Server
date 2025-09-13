package com.clubber.domain.domains.review.repository;

import com.clubber.domain.domains.review.vo.KeywordCountStatDto;
import java.util.List;

public interface ReviewKeywordCustomRepository {

	List<KeywordCountStatDto> queryReviewKeywordStatsByClubId(Long clubId);

}
