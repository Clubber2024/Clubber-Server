package com.clubber.ClubberServer.domain.user.dto;

import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record GetUserFavoritesResponse(
        @Schema(description = "유저 id", example = "1")
        Long userId,
        List<FavoriteDetailResponse> userFavorites
) {
    public static GetUserFavoritesResponse of(User user, List<Favorite> favorites) {
        List<FavoriteDetailResponse> favoriteDetailResponse = favorites.stream()
                .map(FavoriteDetailResponse::of)
                .toList();

        return GetUserFavoritesResponse.builder()
                .userId(user.getId())
                .userFavorites(favoriteDetailResponse)
                .build();
    }
}
