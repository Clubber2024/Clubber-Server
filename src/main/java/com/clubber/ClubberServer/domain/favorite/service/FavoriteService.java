package com.clubber.ClubberServer.domain.favorite.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.exception.ClubNotFoundException;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.favorite.dto.FavoriteResponse;
import com.clubber.ClubberServer.domain.favorite.exception.ClubAlreadyRegisterdFavoriteException;
import com.clubber.ClubberServer.domain.favorite.exception.FavoriteNotFoundException;
import com.clubber.ClubberServer.domain.favorite.repository.FavoriteRepository;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.exception.UserNotFoundException;
import com.clubber.ClubberServer.domain.user.repository.UserRepository;
import com.clubber.ClubberServer.global.config.security.SecurityUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoriteService {

	private final FavoriteRepository favoriteRepository;

	private final UserRepository userRepository;

	private final ClubRepository clubRepository;

	@Transactional
	public FavoriteResponse createFavorite(Long clubId) {
		Long currentUserId = SecurityUtils.getCurrentUserId();
		User user = userRepository.findById(currentUserId)
			.orElseThrow(() -> UserNotFoundException.EXCEPTION);
		Club club = clubRepository.findClubByIdAndIsDeleted(clubId, false)
			.orElseThrow(() -> ClubNotFoundException.EXCEPTION);

		if (favoriteRepository.existsByUserAndClubAndIsDeleted(user, club, false))
			throw ClubAlreadyRegisterdFavoriteException.EXCEPTION;

		Favorite favorite = favoriteRepository.save(Favorite.create(user, club));
		return FavoriteResponse.from(favorite);
	}

	@Transactional
	public FavoriteResponse deleteFavorite(Long clubId, Long favoriteId) {
		Long currentUserId = SecurityUtils.getCurrentUserId();
		User user = userRepository.findById(currentUserId)
			.orElseThrow(() -> UserNotFoundException.EXCEPTION);

		Favorite favorite = favoriteRepository.findById(favoriteId)
			.orElseThrow(() -> FavoriteNotFoundException.EXCEPTION);

		favorite.checkUser(user.getId());
		favorite.checkClub(clubId);
		favorite.checkAlreadyDeleted();
		favorite.delete();
		return FavoriteResponse.from(favorite);
	}

}
