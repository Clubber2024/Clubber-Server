package com.clubber.ClubberServer.domain.user.dto;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class UserFavoritesResponse {

    @Schema(description = "유저 id", example = "1")
    private final Long userId;

    private final List<FavoriteDetailResponse> userFavorites;

    @AllArgsConstructor
    @Getter
    @Builder
    public static class FavoriteDetailResponse {

        private final Long id;

        private final ClubResponse clubResponse;

        @AllArgsConstructor
        @Getter
        @Builder
        public static class ClubResponse {
            private final Long id;
            private final String name;
            private final String type;

            private static ClubResponse of(Club club){
                return ClubResponse.builder()
                        .id(club.getId())
                        .name(club.getName())
                        .type(club.getClubType()).build();
            }
        }

        private static FavoriteDetailResponse of(Favorite favorite){
            ClubResponse clubResponse = ClubResponse.of(favorite.getClub());
            return FavoriteDetailResponse
                    .builder()
                    .id(favorite.getId())
                    .clubResponse(clubResponse).build();
        }
    }

    public static UserFavoritesResponse of (User user, List<Favorite> favorites){
        List<FavoriteDetailResponse> favoriteDetailResponse = favorites.stream()
                .map(FavoriteDetailResponse::of).collect(Collectors.toList());

        return UserFavoritesResponse.builder()
                .userId(user.getId())
                .userFavorites(favoriteDetailResponse).build();

    }
}
