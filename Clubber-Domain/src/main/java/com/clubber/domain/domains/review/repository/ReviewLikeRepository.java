package com.clubber.domain.domains.review.repository;

import com.clubber.domain.domains.review.domain.Review;
import com.clubber.domain.domains.review.domain.ReviewLike;
import com.clubber.domain.domains.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {
    boolean existsByReviewAndUserAndIsDeletedFalse(Review review, User user);
}
