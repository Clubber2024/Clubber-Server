package com.clubber.ClubberServer.domain.user.service;


import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.favorite.repository.FavoriteRepository;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.dto.UserFavoritesResponse;
import com.clubber.ClubberServer.domain.user.dto.UserProfileResponse;
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
    void 저장된_유저_프로필_조회() throws Exception {

        //given
        User user = User.builder().id(1L).email("clubber@gmail.com").build();
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));

        //when
        UserProfileResponse userProfileResponse = userService.getUserProfile();

        //then
        assertEquals(user.getEmail(), userProfileResponse.getEmail());
        assertEquals(user.getId(), userProfileResponse.getId());
    }

}
