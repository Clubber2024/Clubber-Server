package com.clubber.ClubberServer.integration.domain.favorite.service;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.favorite.exception.ClubAlreadyRegisterdFavoriteException;
import com.clubber.ClubberServer.domain.favorite.repository.FavoriteRepository;
import com.clubber.ClubberServer.domain.favorite.service.FavoriteService;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.repository.UserRepository;
import com.clubber.ClubberServer.global.config.security.AuthDetails;
import com.clubber.ClubberServer.integration.util.fixture.ClubFixture;
import com.clubber.ClubberServer.integration.util.fixture.UserFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@ActiveProfiles("test")
public class FavoriteServiceTest {
    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UserRepository userRepository;

    private void fromSecurityContext(User user) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        AuthDetails adminDetails = new AuthDetails(user.getId().toString(), "USER");
        UsernamePasswordAuthenticationToken adminToken = new UsernamePasswordAuthenticationToken(
                adminDetails, "user", adminDetails.getAuthorities());
        context.setAuthentication(adminToken);
        SecurityContextHolder.setContext(context);
    }

    @Test
    void 즐겨찾기_추가() {
        //given
        Club club = clubRepository.save(ClubFixture.aClub().build());
        User user = userRepository.save(UserFixture.aUser().build());
        fromSecurityContext(user);

        //when
        favoriteService.createFavorite(club.getId());

        //then
        assertThat(favoriteRepository.existsByUserAndClubAndIsDeleted(user, club, false)).isEqualTo(true);
    }

    @Test
    void 기존_즐겨찾기_추가_에러() {
        //given
        Club club = clubRepository.save(ClubFixture.aClub().build());
        User user = userRepository.save(UserFixture.aUser().build());
        fromSecurityContext(user);

        favoriteRepository.save(Favorite.from(user, club));

        //when & then
        assertThatThrownBy(() -> favoriteService.createFavorite(club.getId()))
                .isInstanceOf(ClubAlreadyRegisterdFavoriteException.class);
    }

    @Test
    void 즐겨찾기_삭제() {
        //given
        Club club = clubRepository.save(ClubFixture.aClub().build());
        User user = userRepository.save(UserFixture.aUser().build());
        fromSecurityContext(user);

        Favorite favorite = favoriteRepository.save(Favorite.from(user, club));

        //when
        favoriteService.deleteFavorite(club.getId(), favorite.getId());

        //then
        Favorite deletedFavorite = favoriteRepository.findById(favorite.getId()).get();
        assertThat(deletedFavorite.isDeleted()).isEqualTo(true);
    }
}
