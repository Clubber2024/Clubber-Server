package com.clubber.ClubberServer.domain.favorite.service;

import com.clubber.ClubberServer.domain.user.service.UserReadService;
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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoriteService {

	private final FavoriteRepository favoriteRepository;

	private final ClubRepository clubRepository;

	private final UserReadService userReadService;

	@Transactional
	public FavoriteResponse createFavorite(Long clubId) {
		User user = userReadService.getUser();
		Club club = clubRepository.findClubByIdAndIsDeleted(clubId, false)
			.orElseThrow(() -> ClubNotFoundException.EXCEPTION);

		if (favoriteRepository.existsByUserAndClubAndIsDeleted(user, club, false)) {
			throw ClubAlreadyRegisterdFavoriteException.EXCEPTION;
		}

		Favorite favorite = favoriteRepository.save(Favorite.create(user, club));
		return FavoriteResponse.from(favorite);
	}

	@Transactional
	public FavoriteResponse deleteFavorite(Long clubId, Long favoriteId) {
		User user = userReadService.getUser();

		Favorite favorite = favoriteRepository.findByIdAndIsDeleted(favoriteId, false)
			.orElseThrow(() -> FavoriteNotFoundException.EXCEPTION);

		favorite.checkUser(user.getId());
		favorite.checkClub(clubId);
		favorite.checkAlreadyDeleted();
		favorite.delete();
		return FavoriteResponse.from(favorite);
	}

}
