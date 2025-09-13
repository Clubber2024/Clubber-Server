package com.clubber.global.event.review.approve;

import com.clubber.domain.domains.review.domain.Review;
import com.clubber.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ReviewApproveEventHandler {

    private final TaskScheduler taskScheduler;

    private final ReviewService reviewService;

    @EventListener
    public void listenReviewApproveEvent(ReviewApproveEvent event) {
        taskScheduler.schedule(
                () -> {
                    Review review = event.getReview();
                    reviewService.saveReview(review);
                }
                , Instant.now().plusSeconds(30));
    }
}
