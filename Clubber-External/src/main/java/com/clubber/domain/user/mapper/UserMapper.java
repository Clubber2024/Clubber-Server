package com.clubber.domain.user.mapper;

import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.domains.favorite.domain.Favorite;
import com.clubber.domain.favorite.dto.GetFavoriteDetailsResponse;
import com.clubber.domain.domains.review.domain.Review;
import com.clubber.domain.review.util.ReviewUtil;
import com.clubber.domain.domains.user.domain.User;
import com.clubber.global.common.page.PageResponse;
import com.clubber.domain.user.dto.*;
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

    public GetUserFavoritesResponse getGetUserFavoritesResponse(User user, List<Favorite> favorites) {
        List<FavoriteDetailResponse> favoriteDetailResponses = favorites.stream()
                .map(
                        favorite -> {
                            Club club = favorite.getClub();
                            FavoriteClubDetailResponse favoriteClubDetailResponse = FavoriteClubDetailResponse.of(club);
                            return FavoriteDetailResponse.of(favorite, favoriteClubDetailResponse);
                        })
                .toList();
        return GetUserFavoritesResponse.of(user, favoriteDetailResponses);
    }
}
