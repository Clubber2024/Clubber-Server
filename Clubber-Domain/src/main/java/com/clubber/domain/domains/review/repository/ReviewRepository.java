package com.clubber.domain.domains.review.repository;

import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.domains.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewCustomRepository {

    List<Review> findByClubOrderByIdDesc(Club club);

    List<Review> findAllByClub(Club club);
}
