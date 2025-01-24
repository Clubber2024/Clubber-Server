package com.clubber.ClubberServer.domain.review.repository;

import com.clubber.ClubberServer.domain.review.domain.ReviewKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewKeywordRepository extends JpaRepository<ReviewKeyword, Long>,
	ReviewKeywordCustomRepository {

}
