package com.clubber.ClubberServer.domain.review.repository;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.review.domain.ApprovedStatus;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.user.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByUserAndClub(User user, Club club);

    List<Review> findByApprovedStatusAndClub(ApprovedStatus status, Club club);

    List<Review> findAllByUserId(Long userId);
}
