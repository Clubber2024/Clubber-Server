package com.clubber.domain.review.service;

import com.clubber.domain.domains.review.domain.Review;
import com.clubber.domain.domains.review.domain.ReviewLike;
import com.clubber.domain.domains.review.implement.ReviewReader;
import com.clubber.domain.domains.review.implement.ReviewValidator;
import com.clubber.domain.domains.review.repository.ReviewLikeRepository;
import com.clubber.domain.domains.user.domain.User;
import com.clubber.domain.review.mapper.ReviewMapper;
import com.clubber.domain.user.implement.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewLikeService {

    private final UserReader userReader;
    private final ReviewReader reviewReader;
    private final ReviewValidator reviewValidator;
    private final ReviewMapper reviewMapper;
    private final ReviewLikeRepository reviewLikeRepository;

    @Transactional
    public void createReviewLike(Long reviewId) {
        User user = userReader.getCurrentUser();
        Review review = reviewReader.findById(reviewId);
        reviewValidator.validateReviewExists(user, review);
        ReviewLike reviewLike = reviewMapper.toReviewLike(review, user);
        reviewLikeRepository.save(reviewLike);
    }

    @Transactional
    public void deleteReviewLike(Long reviewId) {
        User user = userReader.getCurrentUser();
        Review review = reviewReader.findById(reviewId);
        ReviewLike userReviewLike = reviewReader.findUserReviewLike(review, user);
        userReviewLike.delete();
    }
}
