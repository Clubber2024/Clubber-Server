package com.clubber.ClubberServer.domain.review.repository;

import com.clubber.ClubberServer.domain.review.domain.ReviewKeyword;
import com.clubber.ClubberServer.domain.review.dto.response.KeywordStat;

import java.util.List;

public interface ReviewKeywordCustomRepository {

    List<ReviewKeyword> queryReviewKeywordByClubId(Long clubId);

    List<KeywordStat> queryReviewKeywordStatsByClubId(Long clubId);

    List<ReviewKeyword> queryReviewKeywordByUserId(Long userId);

}
