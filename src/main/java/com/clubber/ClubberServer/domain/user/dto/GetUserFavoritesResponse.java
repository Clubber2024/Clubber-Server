package com.clubber.ClubberServer.domain.user.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.user.domain.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetUserFavoritesResponse {

	@Schema(description = "유저 id", example = "1")
	private final Long userId;

	private final List<FavoriteDetailResponse> userFavorites;

	public static GetUserFavoritesResponse of(User user, List<Favorite> favorites) {
		List<FavoriteDetailResponse> favoriteDetailResponse = favorites.stream()
			.map(FavoriteDetailResponse::of).collect(Collectors.toList());

		return GetUserFavoritesResponse.builder()
			.userId(user.getId())
			.userFavorites(favoriteDetailResponse).build();

	}
}
