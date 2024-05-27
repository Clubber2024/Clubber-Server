package com.clubber.ClubberServer.domain.favorite.dto;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class FavoriteResponse {

    @Schema(description = "즐겨찾기 id", example = "1")
    private final Long favoriteId;

    @Schema(description = "유저 id", example = "1")
    private final Long userId;

    @Schema(description = "동아리 id", example = "1")
    private final Long clubId;

    public static FavoriteResponse from(Favorite favorite){
        return FavoriteResponse.builder()
                .favoriteId(favorite.getId())
                .userId(favorite.getUser().getId())
                .clubId(favorite.getClub().getId())
                .build();
    }
}
