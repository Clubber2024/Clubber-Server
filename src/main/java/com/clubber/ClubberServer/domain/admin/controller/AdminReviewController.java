package com.clubber.ClubberServer.domain.admin.controller;


import com.clubber.ClubberServer.domain.admin.dto.GetAdminsReviewByStatusResponse;
import com.clubber.ClubberServer.domain.admin.dto.GetAdminsReviewsResponse;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsReviewApprovedStatusResponse;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsReviewStatusRequest;
import com.clubber.ClubberServer.domain.admin.service.AdminReviewService;
import com.clubber.ClubberServer.domain.review.domain.ApprovedStatus;
import com.clubber.ClubberServer.domain.review.domain.Review;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admins/reviews")
@Tag(name = "[동아리 계정 리뷰 관련 API]")
public class AdminReviewController {

    private final AdminReviewService adminReviewService;

    @Operation(summary = "동아리 계정에서 승인 대기 목록 조회", description = "승인 대기 중인 리뷰의 한줄평 목록 반환")
    @GetMapping("/pending")
    public List<GetAdminsReviewByStatusResponse> getAdminReviewsByApprovedStatus(){
        return adminReviewService.getAdminReviewsByApprovedStatus();
    }

//    @Operation(summary = "동아리 계정에서 리뷰 승인 요청")
//    @PatchMapping("/{reviewId}/approve")
//    public UpdateAdminsReviewApprovedStatusResponse updateAdminsReviewApprove(@PathVariable Long reviewId){
//        return adminReviewService.updateAdminsReviewApprove(reviewId);
//    }

    @Operation(summary = "동아리 계정에서 리뷰 승인 / 거절 요청")
    @PatchMapping("/decision")
    public UpdateAdminsReviewApprovedStatusResponse updateAdminsReviewsApprovedStatusResponse(@Valid @RequestBody
    UpdateAdminsReviewStatusRequest updateAdminsReviewStatusRequest){
        return adminReviewService.updateAdminsReviewsApprovedStatus(updateAdminsReviewStatusRequest);
    }

//    @Operation(summary = "동아리 계정에서 리뷰 미승인 요청", description = "미승인 시 더 이상 동아리 계정에서 대기 리뷰로 보이지 않음")
//    @PatchMapping("/{reviewId}/reject")
//    public UpdateAdminsReviewApprovedStatusResponse updateAdminsReviewReject(@PathVariable Long reviewId){
//        return adminReviewService.updateAdminsReviewReject(reviewId);
//    }

    @Operation(summary = "동아리 계정 마이페이지 리뷰 목록")
    @GetMapping
    public GetAdminsReviewsResponse getAdminsReviews() {
        return adminReviewService.getAdminsReviews();
    }
}
