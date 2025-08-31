package com.clubber.ClubberServer.domain.favorite.implement;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.favorite.exception.ClubAlreadyRegisterdFavoriteException;
import com.clubber.ClubberServer.domain.favorite.repository.FavoriteRepository;
import com.clubber.ClubberServer.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FavoriteValidator {

	private final FavoriteRepository favoriteRepository;

	public void validateFavoriteExist(User user, Club club) {
		if (favoriteRepository.existsByUserAndClubAndIsDeleted(user, club, false)) {
			throw ClubAlreadyRegisterdFavoriteException.EXCEPTION;
		}
	}
}
