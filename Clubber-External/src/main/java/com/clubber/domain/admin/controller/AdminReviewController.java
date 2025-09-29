package com.clubber.domain.admin.controller;

import com.clubber.domain.domains.review.vo.ClubReviewResponse;
import com.clubber.domain.review.dto.CreateReviewApplyRequest;
import com.clubber.domain.review.dto.GetClubReviewAgreedStatusResponse;
import com.clubber.domain.review.service.AdminReviewService;
import com.clubber.global.common.page.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admins/reviews")
@Tag(name = "[동아리 계정 리뷰 관련 API]", description = "🔐동아리 계정")
public class AdminReviewController {

	private final AdminReviewService adminReviewService;

	@Operation(summary = "동아리 계정 마이페이지 리뷰 목록")
	@GetMapping
	public PageResponse<ClubReviewResponse> getAdminsReviews(Pageable pageable) {
		return adminReviewService.getAdminsReviews(pageable);
	}

	@Operation(summary = "리뷰 답글 달기")
	@PostMapping("/reply/{id}")
	public void createReplyReview(@PathVariable Long id, @RequestBody CreateReviewApplyRequest request) {
		adminReviewService.createReviewApply(id, request);
	}

	@Operation(summary = "리뷰 활성화 상태 조회")
	@GetMapping("/enabled")
	public GetClubReviewAgreedStatusResponse getReviewEnabledStatus() {
		return adminReviewService.getReviewEnabledStatus();
	}

	@Operation(summary = "동아리 리뷰 기능 거절 (리뷰 제공 OFF)")
	@PatchMapping("/disable")
	public void disableClubReview() {
		adminReviewService.disableClubReview();
	}

	@Operation(summary = "동아리 리뷰 기능 거절 (리뷰 제공 ON)")
	@PatchMapping("/enable")
	public void enableClubReview() {
		adminReviewService.enableClubReview();
	}
}
