package com.clubber.domain.domains.review.repository;

import com.clubber.domain.domains.review.domain.ReviewKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewKeywordRepository extends JpaRepository<ReviewKeyword, Long>, ReviewKeywordCustomRepository {
}
