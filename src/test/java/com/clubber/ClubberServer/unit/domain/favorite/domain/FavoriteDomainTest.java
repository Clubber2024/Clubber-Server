package com.clubber.ClubberServer.unit.domain.favorite.domain;

import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.favorite.exception.FavoriteAlreadyDeleteException;
import com.clubber.ClubberServer.integration.util.fixture.FavoriteFixture;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FavoriteDomainTest {

    @Test
    void 즐겨찾기_삭제() {
        //given
        Favorite favorite = FavoriteFixture.aFavorite().build();

        //when
        favorite.delete();

        //then
        assertThat(favorite.isDeleted()).isEqualTo(true);
    }

    @Test
    void 즐겨찾기_기존_이미_삭제_에러() {
        //given
        Favorite favorite = FavoriteFixture.aFavorite().build();
        favorite.delete();

        //when & when
        assertThatThrownBy(favorite::delete)
                .isInstanceOf(FavoriteAlreadyDeleteException.class);
    }
}
