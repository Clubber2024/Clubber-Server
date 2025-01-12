package com.clubber.ClubberServer.global.event.review.approve;

import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.service.ReviewService;
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
                    review.autoUpdateReviewStatus();
                    reviewService.saveReview(review);
                }
                , Instant.now().plusSeconds(30));
    }
}
