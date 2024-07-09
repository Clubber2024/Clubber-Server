package com.clubber.ClubberServer.domain.review.repository;

import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.domain.ReviewKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewKeywordRepository extends JpaRepository<ReviewKeyword, Long>, ReviewKeywordCustomRepository {
    List<ReviewKeyword> findAllByReviewId(Long reviewId);
}
