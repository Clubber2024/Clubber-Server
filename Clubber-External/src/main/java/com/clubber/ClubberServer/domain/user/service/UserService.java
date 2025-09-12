package com.clubber.ClubberServer.domain.user.service;

import com.clubber.domain.domains.club.domain.Club;
import com.clubber.ClubberServer.domain.club.implement.ClubReader;
import com.clubber.domain.domains.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.favorite.dto.GetFavoriteDetailsResponse;
import com.clubber.ClubberServer.domain.favorite.implement.FavoriteReader;
import com.clubber.domain.domains.review.domain.Review;
import com.clubber.ClubberServer.domain.review.repository.ReviewRepository;
import com.clubber.domain.domains.user.domain.User;
import com.clubber.ClubberServer.domain.user.dto.GetIsUserFavoriteResponse;
import com.clubber.ClubberServer.domain.user.dto.GetUserFavoritesResponse;
import com.clubber.ClubberServer.domain.user.dto.GetUserProfileResponse;
import com.clubber.ClubberServer.domain.user.dto.GetUserReviewsResponse;
import com.clubber.ClubberServer.domain.user.implement.UserReader;
import com.clubber.ClubberServer.domain.user.mapper.UserMapper;
import com.clubber.ClubberServer.global.common.page.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final ReviewRepository reviewRepository;

    private final UserReader userReader;

    private final ClubReader clubReader;

    private final UserMapper userMapper;

    private final FavoriteReader favoriteReader;

    public GetUserProfileResponse getUserProfile() {
        User user = userReader.getCurrentUser();
        return GetUserProfileResponse.from(user);
    }

    public GetUserFavoritesResponse getUserFavorites() {
        User user = userReader.getCurrentUser();
        List<Favorite> favorites = favoriteReader.findUserFavorites(user.getId());
        return userMapper.getGetUserFavoritesResponse(user, favorites);
    }

    public GetUserReviewsResponse getUserReviews() {
        User user = userReader.getCurrentUser();
        List<Review> reviews = reviewRepository.queryReviewByUserOrderByIdDesc(user);
        return userMapper.getGetUserReviewResponse(user, reviews);
    }

    public PageResponse<GetFavoriteDetailsResponse> getUserFavoritesPagination(Pageable pageable) {
        User user = userReader.getCurrentUser();
        Page<Favorite> favorites = favoriteReader.findUserFavoritePages(user.getId(), pageable);
        return userMapper.getUserFavoritePageResponse(favorites);
    }

    public GetIsUserFavoriteResponse getIsUserFavorite(Long clubId) {
        User user = userReader.getCurrentUser();
        Club club = clubReader.findById(clubId);
        boolean isFavorite = favoriteReader.isFavoriteExist(user, club);
        return GetIsUserFavoriteResponse.of(club, isFavorite);
    }
}
