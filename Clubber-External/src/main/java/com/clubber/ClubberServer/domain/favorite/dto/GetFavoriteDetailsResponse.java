package com.clubber.ClubberServer.domain.favorite.dto;

import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.domains.favorite.domain.Favorite;
import com.clubber.common.vo.image.ImageVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record GetFavoriteDetailsResponse(
        @Schema(description = "즐겨찾기 id", example = "1")
        Long favoriteId,
        @Schema(description = "동아리 id", example = "1")
        Long clubId,
        @Schema(description = "동아리 이름", example = "로타랙트")
        String clubName,
        @Schema(description = "동아리 종류", example = "중앙동아리")
        String clubType,
        @Schema(description = "동아리 이미지 url")
        ImageVO imageUrl
) {
    public static GetFavoriteDetailsResponse of(Favorite favorite, Club club) {
        return GetFavoriteDetailsResponse.builder()
                .favoriteId(favorite.getId())
                .clubId(club.getId())
                .clubName(club.getName())
                .clubType(club.getClubType().getTitle())
                .imageUrl(club.getImageUrl())
                .build();
    }
}
