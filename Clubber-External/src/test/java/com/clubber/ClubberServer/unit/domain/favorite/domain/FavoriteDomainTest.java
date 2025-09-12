package com.clubber.ClubberServer.unit.domain.favorite.domain;

import com.clubber.domain.domains.club.domain.Club;
import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.domain.domains.favorite.exception.FavoriteAlreadyDeleteException;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.integration.util.fixture.ClubFixture;
import com.clubber.ClubberServer.integration.util.fixture.FavoriteFixture;
import com.clubber.ClubberServer.integration.util.fixture.UserFixture;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FavoriteDomainTest {

    @Test
    void 즐겨찾기_삭제() {
        //given
        Club club = ClubFixture.aClub().id(1L).build();
        User user = UserFixture.aUser().id(1L).build();
        Favorite favorite = FavoriteFixture.aFavorite().club(club).user(user).build();

        //when
        favorite.delete(club.getId(), user.getId());

        //then
        assertThat(favorite.isDeleted()).isEqualTo(true);
    }

    @Test
    void 즐겨찾기_기존_이미_삭제_에러() {
        //given
        Club club = ClubFixture.aClub().id(1L).build();
        User user = UserFixture.aUser().id(1L).build();
        Favorite favorite = FavoriteFixture.aFavorite()
                .club(club)
                .user(user)
                .isDeleted(true)
                .build();

        //when & when
        assertThatThrownBy(() -> favorite.delete(user.getId(), club.getId()))
                .isInstanceOf(FavoriteAlreadyDeleteException.class);
    }
}
