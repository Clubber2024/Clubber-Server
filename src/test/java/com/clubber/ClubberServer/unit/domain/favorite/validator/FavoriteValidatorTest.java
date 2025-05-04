package com.clubber.ClubberServer.unit.domain.favorite.validator;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.favorite.exception.FavoriteNotMatchClubException;
import com.clubber.ClubberServer.domain.favorite.exception.FavoriteNotMatchUserException;
import com.clubber.ClubberServer.domain.favorite.implement.FavoriteValidator;
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
        final Long userId = 1L;
        final Long wrongUserId = 2L;

        User user = UserFixture.aUser()
                .id(userId)
                .build();

        Club club = ClubFixture.aClub().build();
        Favorite favorite = Favorite.create(user, club);

        UserFixture.aUser()
                .id(wrongUserId)
                .build();

        //when & then
        assertThatThrownBy(() -> favoriteValidator.validateDeleteFavorite(favorite, wrongUserId, club.getId()))
                .isInstanceOf(FavoriteNotMatchUserException.class);
    }

    @Test
    void 즐겨찾기_삭제_다른_동아리_에러() {
        //given
        final Long clubId = 1L;
        final Long wrongClubId = 2L;

        Club club = ClubFixture.aClub()
                .id(clubId)
                .build();
        User user = UserFixture.aUser().build();
        Favorite favorite = Favorite.create(user, club);

        //when
        assertThatThrownBy(() -> favoriteValidator.validateDeleteFavorite(favorite, user.getId(), wrongClubId))
                .isInstanceOf(FavoriteNotMatchClubException.class);
    }
}
