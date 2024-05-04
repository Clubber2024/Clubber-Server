package com.clubber.ClubberServer.domain.review.repository;

import com.clubber.ClubberServer.domain.review.domain.ReviewKeyword;
import com.clubber.ClubberServer.domain.review.dto.KeywordStats;
import com.querydsl.core.Tuple;
import java.util.EnumMap;
import java.util.List;

public interface ReviewKeywordCustomRepository {

    List<ReviewKeyword> queryReviewKeywordByClubId(Long clubId);

    List<KeywordStats> queryReviewKeywordStatsByClubId(Long clubId);

}
