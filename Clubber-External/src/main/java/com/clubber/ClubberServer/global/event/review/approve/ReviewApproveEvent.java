package com.clubber.ClubberServer.global.event.review.approve;

import com.clubber.ClubberServer.domain.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReviewApproveEvent {
    private Review review;
}
