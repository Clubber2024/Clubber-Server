package com.clubber.ClubberServer.domain.review.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clubber.ClubberServer.domain.review.dto.GetClubReviewAgreedStatusResponse;
import com.clubber.ClubberServer.domain.review.dto.GetClubReviewsKeywordStatsResponse;
import com.clubber.ClubberServer.domain.review.dto.GetClubReviewsPageResponse;
import com.clubber.ClubberServer.domain.review.dto.CreateClubReviewRequest;
import com.clubber.ClubberServer.domain.review.dto.CreateClubReviewResponse;
import com.clubber.ClubberServer.domain.review.dto.GetClubReviewsSliceResponse;
import com.clubber.ClubberServer.domain.review.service.ReviewService;
import com.clubber.ClubberServer.global.config.swagger.DisableSwaggerSecurity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/clubs/{clubId}/reviews")
@RequiredArgsConstructor
@Tag(name = "[리뷰]")
public class ReviewController {

	private final ReviewService reviewService;

	@Operation(summary = "동아리 리뷰 동의 여부 반환")
	@DisableSwaggerSecurity
	@GetMapping("/agree")
	public GetClubReviewAgreedStatusResponse getClubReviewAgreedStatus(@PathVariable Long clubId) {
		return reviewService.getClubReviewAgreedStatus(clubId);
	}

	@Operation(summary = "개별 동아리 별 리뷰 키워드 통계")
	@DisableSwaggerSecurity
	@GetMapping("/keyword-stats")
	public GetClubReviewsKeywordStatsResponse getReviewKeywordStats(@PathVariable Long clubId) {
		return reviewService.getClubReviewKeywordStats(clubId);
	}

	// === v2 ===
	@Operation(summary = "개별 동아리 별 리뷰 조회")
	@DisableSwaggerSecurity
	@GetMapping
	public GetClubReviewsPageResponse getClubReviewsWithContentByClubId(
		@PathVariable Long clubId,
		Pageable pageable) {
		return reviewService.getClubReviewsWithContent(clubId, pageable);
	}

	@Operation(summary = "개별 동아리 별 리뷰 조회 No Offset(Slice)")
	@DisableSwaggerSecurity
	@GetMapping("/slice")
	public GetClubReviewsSliceResponse getClubReviewsWithSliceContent(
		@PathVariable Long clubId, @PageableDefault(size = 5) Pageable pageable,
		@RequestParam(required = false) Long reviewid) {
		return reviewService.getClubReviewsWithSliceContent(clubId, pageable, reviewid);
	}


	@Operation(summary = "동아리 리뷰 작성", description = "리뷰 키워드 항목과 한줄평을 선택하여 작성")
	@PostMapping
	public CreateClubReviewResponse createReviewWithContent(
		@RequestBody @Valid CreateClubReviewRequest reviewRequest,
		@PathVariable Long clubId) {
		return reviewService.createReview(clubId, reviewRequest);
	}

}
