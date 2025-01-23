package com.clubber.ClubberServer.domain.review.repository;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.review.domain.ApprovedStatus;
import com.clubber.ClubberServer.domain.review.domain.Review;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewCustomRepository {

	List<Review> findByApprovedStatusAndClubOrderByIdDesc(ApprovedStatus status, Club club);

	List<Review> findAllByClub(Club club);
}
