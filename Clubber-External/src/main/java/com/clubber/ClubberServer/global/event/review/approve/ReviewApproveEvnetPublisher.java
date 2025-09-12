package com.clubber.ClubberServer.global.event.review.approve;

import com.clubber.domain.domains.review.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewApproveEvnetPublisher {

    private final ApplicationEventPublisher publisher;

    public void throwReviewApproveEvent(Review review) {
        publisher.publishEvent(new ReviewApproveEvent(review));
    }
}
