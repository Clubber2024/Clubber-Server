package com.clubber.ClubberServer.domain.user.service;


import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.favorite.repository.FavoriteRepository;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.dto.UserFavoritesResponse;
import com.clubber.ClubberServer.domain.user.dto.UserProfileResponse;
import com.clubber.ClubberServer.domain.user.exception.UserNotFoundException;
import com.clubber.ClubberServer.domain.user.repository.UserRepository;
import com.clubber.ClubberServer.global.config.security.AuthDetails;
import com.clubber.ClubberServer.global.config.security.SecurityUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    FavoriteRepository favoriteRepository;

    @BeforeEach
    void setUp() {
        AuthDetails userDetails = new AuthDetails("1", "user");
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities()));
    }

    @Test
    void 알맞은_유저가_조회될때_프로필이_조회된다() throws Exception {

        //given
        User user = User.builder().id(1L).email("clubber@gmail.com").build();
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));

        //when
        UserProfileResponse userProfileResponse = userService.getUserProfile();

        //then
        assertEquals(user.getEmail(), userProfileResponse.getEmail());
        assertEquals(user.getId(), userProfileResponse.getId());
    }

    @Test
    void 알맞은유저가_조회되지_않을때_유저존재하지않음예외가_발생한다() throws Exception {

        //given
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        //when
        assertThrows(UserNotFoundException.class, () -> userService.getUserFavorites());

        //then
    }

    @Test
    void 유저의_즐겨찾기가_존재할때_즐겨찾기_목록을_반환한다() throws Exception {
        //given
        User user = User.builder().id(1L).build();
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));

        Club club1 = Club.builder().id(1L).build();
        Club club2 = Club.builder().id(2L).build();

        Favorite favorite1 = Favorite.builder().id(1L).user(user).club(club1).build();
        Favorite favorite2 = Favorite.builder().id(2L).user(user).club(club2).build();
        List<Favorite> favorites = new ArrayList<>();
        favorites.add(favorite1);
        favorites.add(favorite2);

        when(favoriteRepository.queryFavoritesByUserId(any(Long.class)))
                .thenReturn(favorites);

        //when
        UserFavoritesResponse userFavorites = userService.getUserFavorites();

        //then
        assertEquals(userFavorites.getUserId(), user.getId());
        assertEquals(userFavorites.getUserFavorites().size(), 2);
        assertEquals(userFavorites.getUserFavorites().get(0).getFavoriteClub().getClubId(), club1.getId());
    }

    @Test
    void 유저의_즐겨찾기가_없을때_빈리스트가_조회된다() throws Exception {
        //given
        User user = User.builder().id(1L).build();
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(favoriteRepository.queryFavoritesByUserId(any(Long.class)))
                .thenReturn(new ArrayList<>());

        //when
        UserFavoritesResponse userFavorites = userService.getUserFavorites();

        //then
        assertEquals(userFavorites.getUserId(), user.getId());
        assertEquals(userFavorites.getUserFavorites().size(), 0);

    }
}
