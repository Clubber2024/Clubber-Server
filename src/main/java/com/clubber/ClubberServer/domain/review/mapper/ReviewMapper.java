package com.clubber.ClubberServer.domain.review.mapper;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.dto.GetAdminsReviewsResponse;
import com.clubber.ClubberServer.domain.admin.dto.GetAdminsReviewsResponse.GetAdminsReviewDetailsResponse;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.domain.ReviewKeyword;
import com.clubber.ClubberServer.domain.review.dto.response.ClubReviewsWithContentDetailResponse;
import com.clubber.ClubberServer.domain.review.dto.response.CreateClubReviewsWithContentResponse;
import com.clubber.ClubberServer.domain.review.dto.response.GetClubReviewsWithPageContentResponse;
import com.clubber.ClubberServer.domain.review.dto.response.GetClubReviewsWithSliceContentResponse;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.dto.GetUserReviewsResponse;
import com.clubber.ClubberServer.domain.user.dto.GetUserReviewsResponse.UserReviewDetailResponse;
import com.clubber.ClubberServer.global.common.page.PageResponse;
import com.clubber.ClubberServer.global.common.slice.SliceResponse;
import com.clubber.ClubberServer.global.util.SliceUtil;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

	private static Set<String> extractKeywords(Review review) {
		return review.getReviewKeywords()
			.stream()
			.map(ReviewKeyword::getKeywordTitle)
			.collect(Collectors.toCollection(LinkedHashSet::new));
	}

	private static List<UserReviewDetailResponse> getUserReviewDetailResponse(
		List<Review> reviews) {
		return reviews.stream()
			.map(
				review -> {
					Set<String> keywords = extractKeywords(review);
					return UserReviewDetailResponse.of(review, keywords);
				}
			)
			.collect(Collectors.toList());
	}

	private static Page<GetAdminsReviewDetailsResponse> getAdminsReviewDetailsResponses(
		Page<Review> reviewPages) {
		return reviewPages.map(review -> {
			Set<String> keywords = extractKeywords(review);
			return GetAdminsReviewDetailsResponse.of(review, keywords);
		});
	}

	private static Page<ClubReviewsWithContentDetailResponse> getPageClubReviewsWithContentDetailResponse(
		Page<Review> reviewPages) {
		return reviewPages.map(review -> {
			Set<String> keywords = extractKeywords(review);
			return ClubReviewsWithContentDetailResponse.of(review, keywords);
		});
	}

	private static List<ClubReviewsWithContentDetailResponse> getClubReviewsWithContentDetailResponseList(
		List<Review> reviews) {
		return reviews.stream()
			.map(review -> {
					Set<String> keywords = extractKeywords(review);
					return ClubReviewsWithContentDetailResponse.of(review, keywords);
				}
			)
			.collect(Collectors.toList());
	}

	private static Long getLastReviewId(List<Review> reviews, Pageable pageable) {
		if (SliceUtil.hasNext(reviews, pageable)) {
			return SliceUtil.getLastContent(reviews).getId();
		}
		return null;
	}

	public GetClubReviewsWithSliceContentResponse getClubReviewsWithSliceContentResponse(
		Long clubId, List<Review> reviews, Pageable pageable) {
		List<ClubReviewsWithContentDetailResponse> clubReviewsWithContentDetailResponseList = getClubReviewsWithContentDetailResponseList(
			reviews);
		SliceResponse<ClubReviewsWithContentDetailResponse> clubReviewsWithContentDetailResponseSliceResponse = SliceUtil.valueOf(
			clubReviewsWithContentDetailResponseList, pageable);
		Long lastReviewId = getLastReviewId(reviews, pageable);
		return GetClubReviewsWithSliceContentResponse.of(clubId, lastReviewId,
			clubReviewsWithContentDetailResponseSliceResponse);
	}

	public GetClubReviewsWithPageContentResponse getClubReviewsWithPageContentResponse(
		Page<Review> reviews,
		Long clubId) {
		PageResponse<ClubReviewsWithContentDetailResponse> clubReviewsWithContentDetailResponsePageResponse = PageResponse.of(
			getPageClubReviewsWithContentDetailResponse(reviews));
		return GetClubReviewsWithPageContentResponse.of(
			clubReviewsWithContentDetailResponsePageResponse, clubId);
	}

	public CreateClubReviewsWithContentResponse getCreateClubReviewsWithContentResponse(
		Review review) {
		Set<String> keywords = extractKeywords(review);
		return CreateClubReviewsWithContentResponse.of(review, keywords);
	}

	public GetUserReviewsResponse getUserReviewsResponse(User user, List<Review> reviews) {
		List<UserReviewDetailResponse> userReviewDetailResponse = getUserReviewDetailResponse(
			reviews);
		return GetUserReviewsResponse.of(user, userReviewDetailResponse);
	}

	public GetAdminsReviewsResponse getAdminsReviewsResponse(
		Admin admin, Club club, Page<Review> reviews) {
		Page<GetAdminsReviewDetailsResponse> adminsReviewDetailsResponses = getAdminsReviewDetailsResponses(
			reviews);
		return GetAdminsReviewsResponse.of(admin, club,
			PageResponse.of(adminsReviewDetailsResponses));
	}
}
