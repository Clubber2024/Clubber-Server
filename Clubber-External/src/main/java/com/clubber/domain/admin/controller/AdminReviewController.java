package com.clubber.domain.admin.controller;

import com.clubber.domain.admin.dto.UpdateAdminsReviewVerifyResponse;
import com.clubber.domain.domains.review.domain.DeletionStatus;
import com.clubber.domain.domains.review.domain.VerifiedStatus;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clubber.domain.admin.dto.GetAdminPendingReviewsSliceResponse;
import com.clubber.domain.admin.dto.GetAdminsPendingReviews;
import com.clubber.domain.admin.dto.GetAdminsReviewsResponse;
import com.clubber.domain.admin.service.AdminReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admins/reviews")
@Tag(name = "[동아리 계정 리뷰 관련 API]", description = "🔐동아리 계정")
public class AdminReviewController {

	private final AdminReviewService adminReviewService;

	@Operation(summary = "동아리 계정에서 승인 대기 목록 조회", description = "승인 대기 중인 리뷰의 한줄평 목록 반환")
	@GetMapping("/pending")
	public List<GetAdminsPendingReviews> getAdminPendingReviews() {
		return adminReviewService.getAdminPendingReviews();
	}

	@Operation(summary = "동아리 계정에서 승인 대기 목록 조회 (더보기)", description = "추후 적용해주세요")
	@GetMapping("/pending/slice")
	public GetAdminPendingReviewsSliceResponse getAdminPendingReviewsWithSliceResponses(
		@PageableDefault Pageable pageable, @RequestParam(required = false) Long lastReviewId) {
		return adminReviewService.getAdminPendingReviewsWithSliceResponse(pageable, lastReviewId);
	}

	@Operation(summary = "동아리 계정 마이페이지 리뷰 목록")
	@GetMapping
	public GetAdminsReviewsResponse getAdminsReviews(Pageable pageable,
		@RequestParam(required = false) DeletionStatus deletionStatus,
		@RequestParam(required = false) VerifiedStatus verifiedStatus) {
		return adminReviewService.getAdminsReviews(pageable, deletionStatus, verifiedStatus);
	}

	@Operation(summary = "리뷰 인증")
	@PatchMapping("/verify/{reviewId}")
	public UpdateAdminsReviewVerifyResponse updateAdminsReviewVerify(@PathVariable Long reviewId) {
		return adminReviewService.updateAdminsReviewVerify(reviewId);
	}
}
