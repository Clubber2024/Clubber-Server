package com.clubber.ClubberServer.domain.favorite.service;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.exception.ClubNotFoundException;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.favorite.dto.FavoriteResponse;
import com.clubber.ClubberServer.domain.favorite.exception.FavoriteNotFoundException;
import com.clubber.ClubberServer.domain.favorite.repository.FavoriteRepository;
import com.clubber.ClubberServer.domain.favorite.validator.FavoriteValidator;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.service.UserReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteService {

	private final FavoriteValidator favoriteValidator;

	private final FavoriteRepository favoriteRepository;

	private final ClubRepository clubRepository;

	private final UserReadService userReadService;

	@Transactional
	public FavoriteResponse createFavorite(Long clubId) {
		User user = userReadService.getUser();
		Club club = clubRepository.findClubByIdAndIsDeleted(clubId, false)
			.orElseThrow(() -> ClubNotFoundException.EXCEPTION);

		favoriteValidator.validateFavoriteExist(user, club);

		Favorite favorite = favoriteRepository.save(Favorite.create(user, club));
		return FavoriteResponse.from(favorite);
	}

	@Transactional
	public FavoriteResponse deleteFavorite(Long clubId, Long favoriteId) {
		User user = userReadService.getUser();

		Favorite favorite = favoriteRepository.findByIdAndIsDeleted(favoriteId, false)
			.orElseThrow(() -> FavoriteNotFoundException.EXCEPTION);

		favoriteValidator.validateDeleteFavorite(favorite, user, clubId);
		favorite.delete();
		return FavoriteResponse.from(favorite);
	}
}
