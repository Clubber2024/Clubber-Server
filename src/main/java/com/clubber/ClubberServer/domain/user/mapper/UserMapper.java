package com.clubber.ClubberServer.domain.user.mapper;

import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.util.ReviewUtil;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.dto.GetUserReviewsResponse;
import com.clubber.ClubberServer.domain.user.dto.UserReviewResponse;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

	// 리뷰 조회 (일반 회원)
	public GetUserReviewsResponse getGetUserReviewResponse(User user, List<Review> reviews) {
		List<UserReviewResponse> userReviewResponse = UserMapper.getUserReviewResponse(
			reviews);
		return GetUserReviewsResponse.of(user, userReviewResponse);
	}

	public static List<UserReviewResponse> getUserReviewResponse(
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
}
