package com.clubber.ClubberServer.domain.review.mapper;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.dto.GetAdminsReviewsResponse;
import com.clubber.ClubberServer.domain.admin.dto.GetAdminsReviewsResponse.GetAdminsReviewDetailsResponse;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.dto.response.ClubReviewsWithContentDetailResponse;
import com.clubber.ClubberServer.domain.review.dto.response.CreateClubReviewsWithContentResponse;
import com.clubber.ClubberServer.domain.review.dto.response.GetClubReviewsWithPageContentResponse;
import com.clubber.ClubberServer.domain.review.dto.response.GetClubReviewsWithSliceContentResponse;
import com.clubber.ClubberServer.domain.review.util.ReviewUtil;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.dto.GetUserReviewsResponse;
import com.clubber.ClubberServer.domain.user.dto.GetUserReviewsResponse.UserReviewDetailResponse;
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
	public GetClubReviewsWithPageContentResponse getClubReviewsWithPageContentResponse(Page<Review> reviews,
		Long clubId) {
		PageResponse<ClubReviewsWithContentDetailResponse> clubReviewsWithContentDetailPageResponse = toClubReviewsWithContentDetailPageResponse(
			reviews);
		return GetClubReviewsWithPageContentResponse.of(
			clubReviewsWithContentDetailPageResponse, clubId);
	}

	private static PageResponse<ClubReviewsWithContentDetailResponse> toClubReviewsWithContentDetailPageResponse(
		Page<Review> reviewPages) {
		Page<ClubReviewsWithContentDetailResponse> clubReviewsWithContentDetailResponsePage =
			reviewPages.map(review -> {
			Set<String> keywords = ReviewUtil.extractKeywords(review);
			return ClubReviewsWithContentDetailResponse.of(review, keywords);
		});
		return PageResponse.of(clubReviewsWithContentDetailResponsePage);
	}

	// 동아리 별 리뷰 조회 (No Offset)
	public GetClubReviewsWithSliceContentResponse getClubReviewsWithSliceContentResponse(
		Long clubId, List<Review> reviews, Pageable pageable) {
		List<ClubReviewsWithContentDetailResponse> clubReviewsWithContentDetailResponseList = getClubReviewsWithContentDetailResponseList(
			reviews);
		SliceResponse<ClubReviewsWithContentDetailResponse> clubReviewsWithContentDetailResponseSliceResponse = SliceUtil.valueOf(
			clubReviewsWithContentDetailResponseList, pageable);
		Long lastReviewId = ReviewUtil.getLastReviewId(reviews, pageable);
		return GetClubReviewsWithSliceContentResponse.of(clubId, lastReviewId,
			clubReviewsWithContentDetailResponseSliceResponse);
	}

	private static List<ClubReviewsWithContentDetailResponse> getClubReviewsWithContentDetailResponseList(
		List<Review> reviews) {
		return reviews.stream()
			.map(review -> {
					Set<String> keywords = ReviewUtil.extractKeywords(review);
					return ClubReviewsWithContentDetailResponse.of(review, keywords);
				}
			)
			.collect(Collectors.toList());
	}

	// 리뷰 조회 (일반 회원)
	public GetUserReviewsResponse getUserReviewsResponse(User user, List<Review> reviews) {
		List<UserReviewDetailResponse> userReviewDetailResponse = getUserReviewDetailResponse(
			reviews);
		return GetUserReviewsResponse.of(user, userReviewDetailResponse);
	}

	private static List<UserReviewDetailResponse> getUserReviewDetailResponse(
		List<Review> reviews) {
		return reviews.stream()
			.map(
				review -> {
					Set<String> keywords = ReviewUtil.extractKeywords(review);
					return UserReviewDetailResponse.of(review, keywords);
				}
			)
			.collect(Collectors.toList());
	}

	// 리뷰 조회 (관리자)
	public GetAdminsReviewsResponse getAdminsReviewsResponse(
		Admin admin, Club club, Page<Review> reviews) {
		PageResponse<GetAdminsReviewDetailsResponse> adminsReviewDetailsPageResponse = getAdminsReviewDetailsResponses(
			reviews);
		return GetAdminsReviewsResponse.of(admin, club, adminsReviewDetailsPageResponse);
	}

	private static PageResponse<GetAdminsReviewDetailsResponse> getAdminsReviewDetailsResponses(
		Page<Review> reviewPages) {
		Page<GetAdminsReviewDetailsResponse> getAdminsReviewDetailsResponsePage = reviewPages.map(
			review -> {
				Set<String> keywords = ReviewUtil.extractKeywords(review);
				return GetAdminsReviewDetailsResponse.of(review, keywords);
			});
		return PageResponse.of(getAdminsReviewDetailsResponsePage);
	}

	// 리뷰 작성
	public CreateClubReviewsWithContentResponse getCreateClubReviewsWithContentResponse(
		Review review) {
		Set<String> keywords = ReviewUtil.extractKeywords(review);
		return CreateClubReviewsWithContentResponse.of(review, keywords);
	}
}
