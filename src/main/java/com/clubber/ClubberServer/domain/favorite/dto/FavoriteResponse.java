package com.clubber.ClubberServer.domain.favorite.dto;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record FavoriteResponse(@Schema(description = "즐겨찾기 id", example = "1")
                               Long favoriteId,
                               @Schema(description = "유저 id", example = "1")
                               Long userId,
                               @Schema(description = "동아리 id", example = "1")
                               Long clubId
) {
    public static FavoriteResponse of(Favorite favorite, Long clubId, Long userId) {
        return FavoriteResponse.builder()
                .favoriteId(favorite.getId())
                .userId(userId)
                .clubId(clubId)
                .build();
    }
}
