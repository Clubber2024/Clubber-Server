package com.clubber.ClubberServer.domain.admin.controller;


import com.clubber.ClubberServer.domain.admin.dto.GetAdminsReviewByStatusResponse;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsReviewApprovedStatusResponse;
import com.clubber.ClubberServer.domain.admin.service.AdminReviewService;
import com.clubber.ClubberServer.domain.review.domain.ApprovedStatus;
import com.clubber.ClubberServer.domain.review.domain.Review;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admins/reviews")
@Tag(name = "[동아리 계정 리뷰 관련 API]")
public class AdminReviewController {

    private final AdminReviewService adminReviewService;

    @Operation(summary = "동아리 계정 승인 상태별 리뷰 조회", description = "승인 대기, 승인 완료된 댓글을 파라미로 전달하여 요청")
    @GetMapping
    public List<GetAdminsReviewByStatusResponse> getAdminReviewsByApprovedStatus(@RequestParam ApprovedStatus approvedStatus){
        return adminReviewService.getAdminReviewsByApprovedStatus(approvedStatus);
    }

    @Operation(summary = "동아리 계정에서 리뷰 승인 요청")
    @PatchMapping("/{reviewId}/approve")
    public UpdateAdminsReviewApprovedStatusResponse updateAdminsReviewApprove(@PathVariable Long reviewId){
        return adminReviewService.updateAdminsReviewApprove(reviewId);
    }

    @PatchMapping("/{reviewId}/reject")
    public UpdateAdminsReviewApprovedStatusResponse updateAdminsReviewReject(@PathVariable Long reviewId){
        return adminReviewService.updateAdminsReviewReject(reviewId); 
    }
}