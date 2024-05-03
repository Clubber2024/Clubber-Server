package com.clubber.ClubberServer.domain.review.repository;

import com.clubber.ClubberServer.domain.review.domain.ReviewKeyword;
import java.util.List;

public interface ReviewKeywordCustomRepository {

    List<ReviewKeyword> queryReviewKeywordByClubId(Long clubId);

}
