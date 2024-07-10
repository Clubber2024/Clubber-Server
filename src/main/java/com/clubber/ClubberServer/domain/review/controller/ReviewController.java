package com.clubber.ClubberServer.domain.review.controller;

import com.clubber.ClubberServer.domain.review.dto.*;
import com.clubber.ClubberServer.domain.review.service.ReviewService;
import com.clubber.ClubberServer.global.config.swagger.DisableSwaggerSecurity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "[리뷰]")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "동아리 리뷰 작성", description = "리뷰 키워드 항목을 선택하여 작성")
    @PostMapping
    public ReviewCreateResponse createReview(@RequestBody @Valid ReviewRequest reviewRequest,
                                             @PathVariable Long clubId){
        return reviewService.createReview(clubId, reviewRequest);
    }

    @Operation(summary = "개별 동아리 별 리뷰 조회")
    @DisableSwaggerSecurity
    @GetMapping
    public ClubReviewResponse getClubReviews(@PathVariable Long clubId){
        return reviewService.getClubReviews(clubId);
    }

    @Operation(summary = "개별 동아리 별 리뷰 키워드 통계")
    @DisableSwaggerSecurity
    @GetMapping("/keyword-stats")
    public ClubReviewKeywordStatsResponse getReviewKeywordStats(@PathVariable Long clubId){
        return reviewService.getClubReviewKeywordStats(clubId);
    }

    // === v2 ===
    @Operation(summary = "개별 동아리 별 리뷰 조회 V2")
    @DisableSwaggerSecurity
    @GetMapping("/v2")
    public ClubReviewsWithContentResponse getClubReviewsWithContentByClubId(@PathVariable Long clubId) {
        return reviewService.getClubReviewsWithContent(clubId);
    }

    @Operation(summary = "동아리 리뷰 작성 V2",description = "리뷰 키워드 항목과 한줄평을 선택하여 작성")
    @PostMapping("/v2")
    public CreateReviewClubWithContentResponse createReviewWithContent(@RequestBody CreateReviewClubWithContentRequest reviewRequest,
                                                                       Long clubId){
        return reviewService.createReviewsByContent(clubId, reviewRequest);
    }


}
