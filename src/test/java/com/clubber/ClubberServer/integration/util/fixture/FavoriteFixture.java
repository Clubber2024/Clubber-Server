package com.clubber.ClubberServer.integration.util.fixture;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.user.domain.User;

public class FavoriteFixture {

    public static Favorite.FavoriteBuilder aFavorite() {
        User user = UserFixture.aUser().build();
        Club club = ClubFixture.aClub().build();
        return Favorite.builder()
                .user(user)
                .club(club);
    }
}
