package com.clubber.domain.favorite.implement;

import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.domains.favorite.exception.ClubAlreadyRegisterdFavoriteException;
import com.clubber.domain.favorite.repository.FavoriteRepository;
import com.clubber.domain.domains.user.domain.User;
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
