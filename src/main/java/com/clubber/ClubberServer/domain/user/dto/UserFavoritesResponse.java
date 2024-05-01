package com.clubber.ClubberServer.domain.user.dto;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.dto.UserFavoritesResponse.FavoriteResponse.ClubResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class UserFavoritesResponse {

    private final Long userId;
    private final List<FavoriteResponse> favorites;

    @AllArgsConstructor
    @Getter
    @Builder
    public static class FavoriteResponse {

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

        private static FavoriteResponse of(Favorite favorite){
            ClubResponse clubResponse = ClubResponse.of(favorite.getClub());
            return FavoriteResponse
                    .builder()
                    .id(favorite.getId())
                    .clubResponse(clubResponse).build();
        }
    }

    public static UserFavoritesResponse of (Long userId, List<Favorite> favorites){
        List<FavoriteResponse> favoriteResponse = favorites.stream()
                .map(FavoriteResponse::of).collect(Collectors.toList());

        return UserFavoritesResponse.builder()
                .userId(userId)
                .favorites(favoriteResponse).build();

    }
}
