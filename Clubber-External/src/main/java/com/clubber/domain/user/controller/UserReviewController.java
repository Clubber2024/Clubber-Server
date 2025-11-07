package com.clubber.domain.user.controller;

import com.clubber.domain.review.dto.UpdateClubReviewContentRequest;
import com.clubber.domain.review.service.ReviewService;
import com.clubber.domain.user.dto.GetUserReviewReportResponse;
import com.clubber.domain.user.dto.GetUserReviewsResponse;
import com.clubber.domain.user.service.UserService;
import com.clubber.global.common.slice.SliceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/review")
@RequiredArgsConstructor
@Tag(name = "[회원 리뷰 API]")
public class UserReviewController {

    private final UserService userService;
    private final ReviewService reviewService;

    @Operation(summary = "회원 리뷰 전체 조회")
    @GetMapping
    public GetUserReviewsResponse getUserReviews() {
        return userService.getUserReviews();
    }

    @Operation(summary = "회원 리뷰 수정")
    @PatchMapping("/{id}")
    public void updateUserReview(@PathVariable Long id, @RequestBody UpdateClubReviewContentRequest request) {
        reviewService.updateReviewContent(id, request.content());
    }

    @Operation(summary = "리뷰 삭제")
    @DeleteMapping("/{id}")
    public void deleteUserReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }

    @Operation(summary = "회원 리뷰 신고 목록 조회")
    @GetMapping("/{id}/reports")
    public SliceResponse<GetUserReviewReportResponse> getUserReviewReports(@PathVariable Long id, @RequestParam(required = false) Long nowReviewReportId) {
        return reviewService.getUserReviewReportResponse(id, nowReviewReportId);
    }
}
