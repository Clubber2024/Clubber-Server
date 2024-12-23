package com.clubber.ClubberServer.domain.admin.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clubber.ClubberServer.domain.admin.dto.GetAdminPendingReviewsWithSliceResponse;
import com.clubber.ClubberServer.domain.admin.dto.GetAdminsReviewByStatusResponse;
import com.clubber.ClubberServer.domain.admin.dto.GetAdminsReviewsResponse;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsReviewApprovedStatusResponse;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsReviewStatusRequest;
import com.clubber.ClubberServer.domain.admin.service.AdminReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admins/reviews")
@Tag(name = "[동아리 계정 리뷰 관련 API]")
public class AdminReviewController {

	private final AdminReviewService adminReviewService;

	@Operation(summary = "동아리 계정에서 승인 대기 목록 조회", description = "승인 대기 중인 리뷰의 한줄평 목록 반환")
	@GetMapping("/pending")
	public List<GetAdminsReviewByStatusResponse> getAdminReviewsByApprovedStatus() {
		return adminReviewService.getAdminReviewsByApprovedStatus();
	}

	@Operation(summary = "동아리 계정에서 승인 대기 목록 조회 (더보기)", description = "추후 적용해주세요")
	@GetMapping("/pending/slice")
	public GetAdminPendingReviewsWithSliceResponse getAdminPendingReviewsWithSliceResponses(
		@PageableDefault Pageable pageable, @RequestParam(required = false) Long lastReviewId){
		return adminReviewService.getAdminPendingReviewsWithSliceResponse(pageable, lastReviewId);
	}

	@Operation(summary = "동아리 계정에서 리뷰 승인 / 거절 요청")
	@PatchMapping("/decision")
	public UpdateAdminsReviewApprovedStatusResponse updateAdminsReviewsApprovedStatusResponse(@Valid @RequestBody
	UpdateAdminsReviewStatusRequest updateAdminsReviewStatusRequest) {
		return adminReviewService.updateAdminsReviewsApprovedStatus(updateAdminsReviewStatusRequest);
	}

	@Operation(summary = "동아리 계정 마이페이지 리뷰 목록")
	@GetMapping
	public GetAdminsReviewsResponse getAdminsReviews(Pageable pageable) {
		return adminReviewService.getAdminsReviews(pageable);
	}
}
