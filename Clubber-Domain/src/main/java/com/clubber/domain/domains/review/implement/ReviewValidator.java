package com.clubber.domain.domains.review.implement;

import com.clubber.domain.domains.review.domain.Review;
import com.clubber.domain.domains.review.exception.ReviewUserNotMatchException;
import com.clubber.domain.domains.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class ReviewValidator {

    public void validateReview(User user, Review review) {
        if (!review.getUser().equals(user)) {
            throw ReviewUserNotMatchException.EXCEPTION;
        }
    }
}
