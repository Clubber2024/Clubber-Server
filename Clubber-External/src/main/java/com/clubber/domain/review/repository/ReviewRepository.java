package com.clubber.domain.review.repository;

import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.domains.review.domain.ApprovedStatus;
import com.clubber.domain.domains.review.domain.Review;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewCustomRepository {

	List<Review> findByApprovedStatusAndClubOrderByIdDesc(ApprovedStatus status, Club club);

	List<Review> findAllByClub(Club club);
}
