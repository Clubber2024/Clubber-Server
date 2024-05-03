package com.clubber.ClubberServer.domain.review.controller;

import com.clubber.ClubberServer.domain.review.dto.ClubReviewKeywordStatsResponse;
import com.clubber.ClubberServer.domain.review.dto.ClubReviewResponse;
import com.clubber.ClubberServer.domain.review.dto.ReviewCreateResponse;
import com.clubber.ClubberServer.domain.review.dto.ReviewRequest;
import com.clubber.ClubberServer.domain.review.service.ReviewService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/clubs/{clubId}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    @PostMapping
    public ReviewCreateResponse createReview(@RequestBody ReviewRequest reviewRequest,
            @PathVariable Long clubId){
        return reviewService.createReview(clubId, reviewRequest);
    }

    @GetMapping
    public ClubReviewResponse getClubReviews(@PathVariable Long clubId){
        return reviewService.getClubReviews(clubId);
    }

    @GetMapping("/keyword-stats")
    public ClubReviewKeywordStatsResponse getReviewKeywordStats(@PathVariable Long clubId){
        return reviewService.getClubReviewKeywordStats(clubId);
    }

}
