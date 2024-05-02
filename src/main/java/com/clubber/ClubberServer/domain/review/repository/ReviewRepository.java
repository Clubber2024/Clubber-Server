package com.clubber.ClubberServer.domain.review.repository;

import com.clubber.ClubberServer.domain.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
