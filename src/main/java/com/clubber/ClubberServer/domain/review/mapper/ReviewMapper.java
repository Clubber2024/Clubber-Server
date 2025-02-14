package com.clubber.ClubberServer.domain.review.mapper;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.domain.ReviewKeyword;
import com.clubber.ClubberServer.domain.review.dto.ClubReviewResponse;
import com.clubber.ClubberServer.domain.review.dto.CreateClubReviewResponse;
import com.clubber.ClubberServer.domain.review.dto.GetClubReviewsKeywordStatsResponse;
import com.clubber.ClubberServer.domain.review.dto.GetClubReviewsPageResponse;
import com.clubber.ClubberServer.domain.review.dto.GetClubReviewsSliceResponse;
import com.clubber.ClubberServer.domain.review.util.ReviewUtil;
import com.clubber.ClubberServer.domain.review.vo.KeywordStatsVO;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.global.common.page.PageResponse;
import com.clubber.ClubberServer.global.common.slice.SliceResponse;
import com.clubber.ClubberServer.global.util.SliceUtil;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

	// 리뷰 작성
	public CreateClubReviewResponse getCreateClubReviewResponse(
		Review review) {
		Set<String> keywords = ReviewUtil.extractKeywords(review);
		return CreateClubReviewResponse.of(review, keywords);
	}

	public GetClubReviewsKeywordStatsResponse getGetClubReviewsKeywordStatsResponse(Club club,
		KeywordStatsVO keywordStatsVO) {
		Map<String, Long> keywordMap = keywordStatsVO.getKeywordMapAsStingKey();
		return GetClubReviewsKeywordStatsResponse.of(club, keywordMap);
	}
}

