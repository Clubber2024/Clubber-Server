package com.clubber.ClubberServer.domain.user.dto;

import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FavoriteDetailResponse {

    @Schema(description = "즐겨찾기 id", example = "1")
    private final Long favoriteId;

    @Schema(description = "즐겨찾기한 동아리")
    private final FavoriteClubDetailResponse favoriteClub;

    public static FavoriteDetailResponse of(Favorite favorite) {
        FavoriteClubDetailResponse favoriteClubDetailResponse = FavoriteClubDetailResponse.of(favorite.getClub());
        return FavoriteDetailResponse
                .builder()
                .favoriteId(favorite.getId())
                .favoriteClub(favoriteClubDetailResponse).build();
    }
}
