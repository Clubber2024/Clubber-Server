package com.clubber.ClubberServer.domain.user.service;


import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.favorite.dto.FavoriteResponse;
import com.clubber.ClubberServer.domain.favorite.dto.GetFavoriteDetailsResponse;
import com.clubber.ClubberServer.domain.favorite.repository.FavoriteRepository;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.dto.GetUserReviewsResponse;
import com.clubber.ClubberServer.domain.review.repository.ReviewRepository;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.dto.GetUserFavoritesResponse;
import com.clubber.ClubberServer.domain.user.dto.GetUsersProfileResponse;
import com.clubber.ClubberServer.domain.user.exception.UserNotFoundException;
import com.clubber.ClubberServer.domain.user.repository.UserRepository;
import com.clubber.ClubberServer.global.config.security.SecurityUtils;
import com.clubber.ClubberServer.global.page.PageResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    private final FavoriteRepository favoriteRepository;

    private final ReviewRepository reviewRepository;

    public GetUsersProfileResponse getUserProfile(){
        Long currentUserId = SecurityUtils.getCurrentUserId();
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
        return GetUsersProfileResponse.of(user);
    }

    public GetUserFavoritesResponse getUserFavorites(){
        Long currentUserId = SecurityUtils.getCurrentUserId();
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        List<Favorite> favorites = favoriteRepository.queryFavoritesByUserId(user.getId());
        return GetUserFavoritesResponse.of(user, favorites);
    }

    // v1- 내가 쓴 리뷰
//    public UserReviewResponse getUserReviews(){
//        Long currentUserId = SecurityUtils.getCurrentUserId();
//        User user = userRepository.findById(currentUserId)
//                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
//        List<ReviewKeyword> reviewKeywords = reviewKeywordRepository.queryReviewKeywordByUserId(
//                currentUserId);
//        return UserReviewResponse.of(user, reviewKeywords);
//    }

    public GetUserReviewsResponse getUserReviews(){
        Long currentUserId = SecurityUtils.getCurrentUserId();
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        List<Review> reviews = reviewRepository.queryReviewByUserOrderByIdDesc(user);
        return GetUserReviewsResponse.of(user, reviews);

    }

    public PageResponse<GetFavoriteDetailsResponse> getUserFavoritesPagination(Pageable pageable) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Page<Favorite> favorites = favoriteRepository.queryFavoritesPageByUserId(currentUserId,
                pageable);
        Page<GetFavoriteDetailsResponse> favoriteResponses = favorites.map(GetFavoriteDetailsResponse::of);
        return PageResponse.of(favoriteResponses);
    }

}
