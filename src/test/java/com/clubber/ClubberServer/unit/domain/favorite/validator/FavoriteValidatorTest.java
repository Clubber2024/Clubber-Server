package com.clubber.ClubberServer.unit.domain.favorite.validator;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.favorite.exception.FavoriteNotMatchUserException;
import com.clubber.ClubberServer.domain.favorite.validator.FavoriteValidator;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.integration.util.fixture.ClubFixture;
import com.clubber.ClubberServer.integration.util.fixture.UserFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class FavoriteValidatorTest {

    @InjectMocks
    private FavoriteValidator favoriteValidator;

    @Test
    void 즐겨찾기_삭제_다른_사용자_에러() {
        //given
        User user = UserFixture.aUser().build();
        Club club = ClubFixture.aClub().build();
        Favorite favorite = Favorite.create(user, club);

        User wrongUser = UserFixture.aUser().id(2L).build();

        //when & then
        assertThatThrownBy(() -> favoriteValidator.validateDeleteFavorite(favorite, wrongUser, club.getId()))
                .isInstanceOf(FavoriteNotMatchUserException.class);
    }
}
