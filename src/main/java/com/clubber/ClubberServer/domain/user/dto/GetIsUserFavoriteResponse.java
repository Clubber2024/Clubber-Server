package com.clubber.ClubberServer.domain.user.dto;

import com.clubber.ClubberServer.domain.club.domain.Club;
import lombok.Builder;

@Builder
public record GetIsUserFavoriteResponse(
	Long clubId,
	boolean isFavorite
) {

	public static GetIsUserFavoriteResponse of(Club club, boolean isFavorite) {
		return GetIsUserFavoriteResponse.builder()
			.clubId(club.getId())
			.isFavorite(isFavorite)
			.build();
	}
}
