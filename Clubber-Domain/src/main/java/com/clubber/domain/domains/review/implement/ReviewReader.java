package com.clubber.domain.domains.review.implement;

import com.clubber.domain.domains.review.domain.Review;
import com.clubber.domain.domains.review.domain.ReviewLike;
import com.clubber.domain.domains.review.exception.ReviewLikeNotFoundException;
import com.clubber.domain.domains.review.exception.ReviewNotFoundException;
import com.clubber.domain.domains.review.repository.ReviewLikeRepository;
import com.clubber.domain.domains.review.repository.ReviewRepository;
import com.clubber.domain.domains.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewReader {
    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;

    public Review findById(Long id) {
        return reviewRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> ReviewNotFoundException.EXCEPTION);
    }

    public ReviewLike findUserReviewLike(Review review, User user) {
        return reviewLikeRepository.findByUserAndReviewAndIsDeletedFalse(review, user)
                .orElseThrow(() -> ReviewLikeNotFoundException.EXCEPTION);
    }
}
