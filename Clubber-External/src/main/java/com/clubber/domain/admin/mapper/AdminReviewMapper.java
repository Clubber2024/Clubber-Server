package com.clubber.domain.admin.mapper;

import com.clubber.domain.admin.domain.Admin;
import com.clubber.domain.admin.dto.AdminReviewResponse;
import com.clubber.domain.admin.dto.GetAdminPendingReviewsSliceResponse;
import com.clubber.domain.admin.dto.GetAdminsPendingReviews;
import com.clubber.domain.admin.dto.GetAdminsReviewsResponse;
import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.domains.review.domain.Review;
import com.clubber.domain.review.util.ReviewUtil;
import com.clubber.global.common.page.PageResponse;
import com.clubber.global.common.slice.SliceResponse;
import com.clubber.global.util.SliceUtil;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class AdminReviewMapper {

	// 리뷰 조회 (관리자)
	public GetAdminsReviewsResponse getGetAdminReviewsResponse(
		Admin admin, Club club, Page<Review> reviews) {
		PageResponse<AdminReviewResponse> adminsReviewDetailsPageResponse = getAdminsReviewResponse(
			reviews);
		return GetAdminsReviewsResponse.of(admin, club, adminsReviewDetailsPageResponse);
	}

	private static PageResponse<AdminReviewResponse> getAdminsReviewResponse(
		Page<Review> reviewPages) {
		Page<AdminReviewResponse> getAdminReviewsPageResponse = reviewPages.map(
			review -> {
				Set<String> keywords = ReviewUtil.extractKeywords(review);
				return AdminReviewResponse.of(review, keywords);
			});
		return PageResponse.of(getAdminReviewsPageResponse);
	}

	//대기 상태 리뷰 조회 (관리자, No-offset)
	public GetAdminPendingReviewsSliceResponse getGetAdminPendingReviewSliceResponse(
		List<Review> reviews, Pageable pageable) {
		List<GetAdminsPendingReviews> getAdminPendingReviewList = getGetAdminPendingReviewList(
			reviews);
		SliceResponse<GetAdminsPendingReviews> getAdminsPendingReviewsSliceResponse = SliceUtil.valueOf(
			getAdminPendingReviewList, pageable);
		Long lastReviewId = ReviewUtil.getLastReviewId(reviews, pageable);
		return GetAdminPendingReviewsSliceResponse.of(getAdminsPendingReviewsSliceResponse,
			lastReviewId);
	}

	//대기 상태 리뷰 조회 (관리자)
	public List<GetAdminsPendingReviews> getGetAdminPendingReviewList(List<Review> reviews) {
		return reviews.stream()
			.map(GetAdminsPendingReviews::from)
			.collect(Collectors.toList());
	}
}
