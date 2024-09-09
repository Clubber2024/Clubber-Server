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

import com.clubber.ClubberServer.domain.review.dto.ClubReviewKeywordStatsResponse;
import com.clubber.ClubberServer.domain.review.dto.ClubReviewResponse;
import com.clubber.ClubberServer.domain.review.dto.GetClubReviewsWithPageContentResponse;
import com.clubber.ClubberServer.domain.review.dto.CreateReviewClubWithContentRequest;
import com.clubber.ClubberServer.domain.review.dto.CreateClubReviewsWithContentResponse;
import com.clubber.ClubberServer.domain.review.dto.GetClubReviewsWithSliceContentResponse;
import com.clubber.ClubberServer.domain.review.service.ReviewService;
import com.clubber.ClubberServer.global.config.swagger.DisableSwaggerSecurity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/clubs/{clubId}/reviews")
@RequiredArgsConstructor
@Tag(name = "[리뷰]")
public class ReviewController {

	private final ReviewService reviewService;

	@Operation(summary = "테스트 해볼 상황이 있어서 살려둔 api", description = "쓰지마세요!")
	@DisableSwaggerSecurity
	@GetMapping("/deprecated")
	public ClubReviewResponse getClubReviews(@PathVariable Long clubId) {
		return reviewService.getClubReviews(clubId);
	}

	@Operation(summary = "개별 동아리 별 리뷰 키워드 통계")
	@DisableSwaggerSecurity
	@GetMapping("/keyword-stats")
	public ClubReviewKeywordStatsResponse getReviewKeywordStats(@PathVariable Long clubId) {
		return reviewService.getClubReviewKeywordStats(clubId);
	}

	// === v2 ===
	@Operation(summary = "개별 동아리 별 리뷰 조회")
	@DisableSwaggerSecurity
	@GetMapping
	public GetClubReviewsWithPageContentResponse getClubReviewsWithContentByClubId(@PathVariable Long clubId,
		Pageable pageable) {
		return reviewService.getClubReviewsWithContent(clubId, pageable);
	}

	@Operation(summary = "개별 동아리 별 리뷰 조회 No Offset(Slice)")
	@DisableSwaggerSecurity
	@GetMapping("/slice")
	public GetClubReviewsWithSliceContentResponse getClubReviewsWithSliceContent(
		@PathVariable Long clubId, @PageableDefault(size = 5) Pageable pageable, @RequestParam(required = false) Long reviewid) {
		return reviewService.getClubReviewsWithSliceContent(clubId, pageable, reviewid);
	}


	@Operation(summary = "동아리 리뷰 작성", description = "리뷰 키워드 항목과 한줄평을 선택하여 작성")
	@PostMapping
	public CreateClubReviewsWithContentResponse createReviewWithContent(
		@RequestBody CreateReviewClubWithContentRequest reviewRequest,
		@PathVariable Long clubId) {
		return reviewService.createReviewsByContent(clubId, reviewRequest);
	}

}
