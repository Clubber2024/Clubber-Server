package com.clubber.ClubberServer.domain.user.dto;

import com.clubber.domain.domains.favorite.domain.Favorite;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record FavoriteDetailResponse(
        @Schema(description = "즐겨찾기 id", example = "1")
        Long favoriteId,
        @Schema(description = "즐겨찾기한 동아리")
        FavoriteClubDetailResponse favoriteClub
) {
    public static FavoriteDetailResponse of(Favorite favorite, FavoriteClubDetailResponse favoriteClubDetailResponse) {
        return FavoriteDetailResponse
                .builder()
                .favoriteId(favorite.getId())
                .favoriteClub(favoriteClubDetailResponse)
                .build();
    }
}
