package com.clubber.ClubberServer.domain.user.mapper;

import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.util.ReviewUtil;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.dto.GetUserReviewsResponse;
import com.clubber.ClubberServer.domain.user.dto.UserReviewResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class UserMapper {

    // 리뷰 조회 (일반 회원)
    public GetUserReviewsResponse getGetUserReviewResponse(User user, List<Review> reviews) {
        List<UserReviewResponse> userReviewResponse = UserMapper.getUserReviewResponse(
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
                .toList();
    }
}
