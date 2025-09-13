package com.clubber.domain.domains.review.repository;

import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.domains.review.domain.Review;
import com.clubber.domain.domains.review.domain.VerifiedStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewCustomRepository {

    List<Review> findByVerifiedStatusAndClubOrderByIdDesc(VerifiedStatus verifiedStatus, Club club);

    List<Review> findAllByClub(Club club);
}
