package com.clubber.ClubberServer.domain.user.mapper;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.favorite.dto.GetFavoriteDetailsResponse;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.util.ReviewUtil;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.dto.GetUserReviewsResponse;
import com.clubber.ClubberServer.domain.user.dto.UserReviewResponse;
import com.clubber.ClubberServer.global.common.page.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class UserMapper {

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
                .toList();
    }

    public PageResponse<GetFavoriteDetailsResponse> getUserFavoritePageResponse(Page<Favorite> favorites) {
        Page<GetFavoriteDetailsResponse> favoriteResponses = favorites.map(
                favorite -> {
                    Club club = favorite.getClub();
                    return GetFavoriteDetailsResponse.of(favorite, club);
                });
        return PageResponse.of(favoriteResponses);
    }
}
