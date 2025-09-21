package com.clubber.domain.domains.review.implement;

import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.domains.review.domain.Review;
import com.clubber.domain.domains.review.exception.ReviewAlreadyLikedException;
import com.clubber.domain.domains.review.exception.ReviewClubNotMatchException;
import com.clubber.domain.domains.review.exception.ReviewUserNotMatchException;
import com.clubber.domain.domains.review.repository.ReviewLikeRepository;
import com.clubber.domain.domains.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewValidator {

    private final ReviewLikeRepository reviewLikeRepository;

    public void validateReview(User user, Review review) {
        if (!review.getUser().equals(user)) {
            throw ReviewUserNotMatchException.EXCEPTION;
        }
    }

    public void validateReviewClub(Club club, Review review) {
        if (!review.getClub().equals(club)) {
            throw ReviewClubNotMatchException.EXCEPTION;
        }
    }

    public void validateReviewExists(User user, Review review) {
        boolean isExists = reviewLikeRepository.existsByReviewAndUserAndIsDeletedFalse(review, user);
        if (isExists) {
            throw ReviewAlreadyLikedException.EXCEPTION;
        }
    }
}
