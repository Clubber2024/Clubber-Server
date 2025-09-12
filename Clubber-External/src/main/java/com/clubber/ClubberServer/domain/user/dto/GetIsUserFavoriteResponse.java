package com.clubber.ClubberServer.domain.user.dto;

import com.clubber.domain.domains.club.domain.Club;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
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
