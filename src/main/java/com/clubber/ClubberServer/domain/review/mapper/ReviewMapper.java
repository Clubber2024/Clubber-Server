package com.clubber.ClubberServer.domain.review.mapper;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.dto.GetAdminPendingReviewsSliceResponse;
import com.clubber.ClubberServer.domain.admin.dto.GetAdminsPendingReviews;
import com.clubber.ClubberServer.domain.admin.dto.GetAdminsReviewsResponse;
import com.clubber.ClubberServer.domain.admin.dto.AdminReviewResponse;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.dto.ClubReviewResponse;
import com.clubber.ClubberServer.domain.review.dto.CreateClubReviewResponse;
import com.clubber.ClubberServer.domain.review.dto.GetClubReviewsPageResponse;
import com.clubber.ClubberServer.domain.review.dto.GetClubReviewsSliceResponse;
import com.clubber.ClubberServer.domain.review.util.ReviewUtil;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.dto.GetUserReviewsResponse;
import com.clubber.ClubberServer.domain.user.dto.UserReviewResponse;
import com.clubber.ClubberServer.global.common.page.PageResponse;
import com.clubber.ClubberServer.global.common.slice.SliceResponse;
import com.clubber.ClubberServer.global.util.SliceUtil;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

	// 동아리 별 리뷰 조회 (page)
	public GetClubReviewsPageResponse getGetClubReviewsPageResponse(Page<Review> reviews,
		Long clubId) {
		PageResponse<ClubReviewResponse> clubReviewsWithContentDetailPageResponse = toClubReviewPageResponse(
			reviews);
		return GetClubReviewsPageResponse.of(
			clubReviewsWithContentDetailPageResponse, clubId);
	}

	private static PageResponse<ClubReviewResponse> toClubReviewPageResponse(
		Page<Review> reviewPages) {
		Page<ClubReviewResponse> clubReviewResponsePage =
			reviewPages.map(review -> {
			Set<String> keywords = ReviewUtil.extractKeywords(review);
			return ClubReviewResponse.of(review, keywords);
		});
		return PageResponse.of(clubReviewResponsePage);
	}

	// 동아리 별 리뷰 조회 (No Offset)
	public GetClubReviewsSliceResponse getClubReviewsSliceResponse(
		Long clubId, List<Review> reviews, Pageable pageable) {
		List<ClubReviewResponse> clubReviewResponseList = getClubReviewResponseList(
			reviews);
		SliceResponse<ClubReviewResponse> clubReviewSliceResponse = SliceUtil.valueOf(
			clubReviewResponseList, pageable);
		Long lastReviewId = ReviewUtil.getLastReviewId(reviews, pageable);
		return GetClubReviewsSliceResponse.of(clubId, lastReviewId,
			clubReviewSliceResponse);
	}

	private static List<ClubReviewResponse> getClubReviewResponseList(
		List<Review> reviews) {
		return reviews.stream()
			.map(review -> {
					Set<String> keywords = ReviewUtil.extractKeywords(review);
					return ClubReviewResponse.of(review, keywords);
				}
			)
			.collect(Collectors.toList());
	}

	// 리뷰 조회 (일반 회원)
	public GetUserReviewsResponse getGetUserReviewResponse(User user, List<Review> reviews) {
		List<UserReviewResponse> userReviewResponse = getUserReviewResponse(
			reviews);
		return GetUserReviewsResponse.of(user, userReviewResponse);
	}

	private static List<UserReviewResponse> getUserReviewResponse(
		List<Review> reviews) {
		return reviews.stream()
			.map(
				review -> {
					Set<String> keywords = ReviewUtil.extractKeywords(review);
					return UserReviewResponse.of(review, keywords);
				}
			)
			.collect(Collectors.toList());
	}

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

	//대기 상태 리뷰 조회 (관리자)
	public List<GetAdminsPendingReviews> getGetAdminPendingReviewList(List<Review> reviews){
		return reviews.stream()
			.map(GetAdminsPendingReviews::from)
			.collect(Collectors.toList());
	}

	//대기 상태 리뷰 조회 (관리자, No-offset)
	public GetAdminPendingReviewsSliceResponse getGetAdminPendingReviewSliceResponse(
		List<Review> reviews, Pageable pageable){
		List<GetAdminsPendingReviews> getAdminPendingReviewList = getGetAdminPendingReviewList(
			reviews);
		SliceResponse<GetAdminsPendingReviews> getAdminsPendingReviewsSliceResponse = SliceUtil.valueOf(
			getAdminPendingReviewList, pageable);
		Long lastReviewId = ReviewUtil.getLastReviewId(reviews, pageable);
		return GetAdminPendingReviewsSliceResponse.of(getAdminsPendingReviewsSliceResponse, lastReviewId);
	}

	// 리뷰 작성
	public CreateClubReviewResponse getCreateClubReviewResponse(
		Review review) {
		Set<String> keywords = ReviewUtil.extractKeywords(review);
		return CreateClubReviewResponse.of(review, keywords);
	}
}
