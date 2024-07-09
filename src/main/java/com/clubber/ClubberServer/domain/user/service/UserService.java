package com.clubber.ClubberServer.domain.user.service;


import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.favorite.repository.FavoriteRepository;
import com.clubber.ClubberServer.domain.review.domain.ReviewKeyword;
import com.clubber.ClubberServer.domain.review.dto.UserReviewResponse;
import com.clubber.ClubberServer.domain.review.repository.ReviewKeywordRepository;
import com.clubber.ClubberServer.domain.review.repository.ReviewRepository;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.dto.UserFavoritesResponse;
import com.clubber.ClubberServer.domain.user.dto.UserProfileResponse;
import com.clubber.ClubberServer.domain.user.exception.UserNotFoundException;
import com.clubber.ClubberServer.domain.user.repository.UserRepository;
import com.clubber.ClubberServer.global.config.security.SecurityUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    private final FavoriteRepository favoriteRepository;

    private final ReviewKeywordRepository reviewKeywordRepository;

    public UserProfileResponse getUserProfile(){
        Long currentUserId = SecurityUtils.getCurrentUserId();
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
        return UserProfileResponse.of(user);
    }

    public UserFavoritesResponse getUserFavorites(){
        Long currentUserId = SecurityUtils.getCurrentUserId();
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        List<Favorite> favorites = favoriteRepository
                .queryFavoritesByUserId(user.getId());
        return UserFavoritesResponse.of(user, favorites);
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

    @Transactional(readOnly = true)
    public UserReviewResponse  getReviewsWithUserId(){
        Long currentUserId = SecurityUtils.getCurrentUserId();
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        List<ReviewKeyword> reviewKeywords=reviewKeywordRepository.queryReviewKeywordByUserId(user.getId());
        return UserReviewResponse.of(user,reviewKeywords);

    }
}
