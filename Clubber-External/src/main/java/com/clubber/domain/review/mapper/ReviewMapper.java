package com.clubber.domain.review.mapper;

import com.clubber.domain.domains.admin.domain.Admin;
import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.domains.review.domain.Review;
import com.clubber.domain.domains.review.domain.ReviewLike;
import com.clubber.domain.domains.review.domain.ReviewReply;
import com.clubber.domain.domains.user.domain.User;
import com.clubber.domain.domains.review.vo.ClubReviewResponse;
import com.clubber.domain.review.dto.CreateClubReviewResponse;
import com.clubber.domain.review.dto.GetClubReviewsKeywordStatsResponse;
import com.clubber.domain.review.dto.GetClubReviewsPageResponse;
import com.clubber.domain.review.dto.GetClubReviewsSliceResponse;
import com.clubber.domain.domains.review.util.ReviewUtil;
import com.clubber.domain.domains.review.vo.KeywordStatsVO;
import com.clubber.global.common.page.PageResponse;
import com.clubber.global.common.slice.SliceResponse;
import com.clubber.global.util.SliceUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    // 동아리 별 리뷰 조회 (page)
    public GetClubReviewsPageResponse getGetClubReviewsPageResponse(Page<ClubReviewResponse> reviewResponses,
                                                                    Long clubId) {
        PageResponse<ClubReviewResponse> pageResponses = PageResponse.of(reviewResponses);
        return GetClubReviewsPageResponse.of(pageResponses, clubId);
    }

//	// 동아리 별 리뷰 조회 (No Offset)
//	public GetClubReviewsSliceResponse getClubReviewsSliceResponse(
//		Long clubId, List<Review> reviews, Pageable pageable) {
//		List<ClubReviewResponse> clubReviewResponseList = getClubReviewResponseList(
//			reviews);
//		SliceResponse<ClubReviewResponse> clubReviewSliceResponse = SliceUtil.valueOf(
//			clubReviewResponseList, pageable);
//		Long lastReviewId = ReviewUtil.getLastReviewId(reviews, pageable);
//		return GetClubReviewsSliceResponse.of(clubId, lastReviewId,
//			clubReviewSliceResponse);
//	}

//	private static List<ClubReviewResponse> getClubReviewResponseList(
//		List<Review> reviews) {
//		return reviews.stream()
//			.map(review -> {
//					Set<String> keywords = ReviewUtil.extractKeywords(review);
//					return ClubReviewResponse.of(review, keywords);
//				}
//			)
//			.collect(Collectors.toList());
//	}

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

    public ReviewReply toReviewApply(Admin admin, Review review, String content) {
        return ReviewReply.builder()
                .content(content)
                .admin(admin)
                .review(review)
                .build();
    }

    public ReviewLike toReviewLike(Review review, User user) {
        return ReviewLike.builder()
                .review(review)
                .user(user)
                .build();
    }
}

