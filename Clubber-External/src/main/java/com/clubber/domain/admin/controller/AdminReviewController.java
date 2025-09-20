package com.clubber.domain.admin.controller;

import com.clubber.domain.admin.dto.GetAdminPendingReviewsSliceResponse;
import com.clubber.domain.admin.dto.GetAdminsPendingReviews;
import com.clubber.domain.admin.dto.GetAdminsReviewsResponse;
import com.clubber.domain.review.service.AdminReviewService;
import com.clubber.domain.domains.review.domain.ReviewSortType;
import com.clubber.domain.review.dto.CreateReviewApplyRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
													 @RequestParam(required = false)ReviewSortType reviewSortType) {
		return adminReviewService.getAdminsReviews(pageable, reviewSortType);
	}

	@Operation(summary = "리뷰 답글 달기")
	@PostMapping("/reply/{id}")
	public void createReplyReview(@PathVariable Long id, @RequestBody CreateReviewApplyRequest request) {
		adminReviewService.createReviewApply(id, request);
	}
}
