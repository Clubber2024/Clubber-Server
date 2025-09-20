package com.clubber.domain.domains.review.implement;

import com.clubber.domain.domains.review.domain.Review;
import com.clubber.domain.domains.review.exception.ReviewNotFoundException;
import com.clubber.domain.domains.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewReader {
    private final ReviewRepository reviewRepository;

    public Review findById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> ReviewNotFoundException.EXCEPTION);
    }
}
