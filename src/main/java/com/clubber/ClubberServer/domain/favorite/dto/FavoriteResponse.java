package com.clubber.ClubberServer.domain.favorite.dto;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class FavoriteResponse {

    private final Long favoriteId;
    private final Long userId;
    private final Long clubId;

    public static FavoriteResponse from(Favorite favorite){
        return FavoriteResponse.builder()
                .favoriteId(favorite.getId())
                .userId(favorite.getUser().getId())
                .clubId(favorite.getClub().getId())
                .build();
    }
}
