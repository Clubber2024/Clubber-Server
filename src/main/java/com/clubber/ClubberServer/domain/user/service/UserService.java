package com.clubber.ClubberServer.domain.user.service;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.exception.ClubNotFoundException;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.favorite.dto.GetFavoriteDetailsResponse;
import com.clubber.ClubberServer.domain.favorite.repository.FavoriteRepository;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.user.dto.GetIsUserFavoriteResponse;
import com.clubber.ClubberServer.domain.user.dto.GetUserReviewsResponse;
import com.clubber.ClubberServer.domain.review.repository.ReviewRepository;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.dto.GetUserFavoritesResponse;
import com.clubber.ClubberServer.domain.user.dto.GetUserProfileResponse;
import com.clubber.ClubberServer.domain.user.mapper.UserMapper;
import com.clubber.ClubberServer.global.common.page.PageResponse;
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

	private final FavoriteRepository favoriteRepository;

	private final ReviewRepository reviewRepository;

	private final UserReadService userReadService;

	private final ClubRepository clubRepository;

	private final UserMapper userMapper;

	public GetUserProfileResponse getUserProfile() {
		User user = userReadService.getUser();
		return GetUserProfileResponse.of(user);
	}

	public GetUserFavoritesResponse getUserFavorites() {
		User user = userReadService.getUser();
		List<Favorite> favorites = favoriteRepository.queryFavoritesByUserId(user.getId());
		return GetUserFavoritesResponse.of(user, favorites);
	}

	public GetUserReviewsResponse getUserReviews() {
		User user = userReadService.getUser();
		List<Review> reviews = reviewRepository.queryReviewByUserOrderByIdDesc(user);
		return userMapper.getGetUserReviewResponse(user, reviews);
	}

	public PageResponse<GetFavoriteDetailsResponse> getUserFavoritesPagination(Pageable pageable) {
		User user = userReadService.getUser();
		Page<Favorite> favorites = favoriteRepository.queryFavoritesPageByUserId(user.getId(),
			pageable);
		Page<GetFavoriteDetailsResponse> favoriteResponses = favorites.map(
			GetFavoriteDetailsResponse::of);
		return PageResponse.of(favoriteResponses);
	}

	public GetIsUserFavoriteResponse getIsUserFavorite(Long clubId) {
		User user = userReadService.getUser();
		Club club = clubRepository.findClubByIdAndIsDeleted(clubId, false)
			.orElseThrow(() -> ClubNotFoundException.EXCEPTION);
		boolean isFavorite = favoriteRepository.existsByUserAndClubAndIsDeleted(user, club, false);
		return GetIsUserFavoriteResponse.of(club, isFavorite);
	}
}
