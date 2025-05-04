package com.clubber.ClubberServer.domain.favorite.service;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.implement.ClubReader;
import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.favorite.dto.FavoriteResponse;
import com.clubber.ClubberServer.domain.favorite.implement.FavoriteAppender;
import com.clubber.ClubberServer.domain.favorite.implement.FavoriteReader;
import com.clubber.ClubberServer.domain.favorite.implement.FavoriteValidator;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.implement.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteValidator favoriteValidator;

    private final FavoriteAppender favoriteAppender;

    private final FavoriteReader favoriteReader;

    private final UserReader userReader;

    private final ClubReader clubReader;

    @Transactional
    public FavoriteResponse createFavorite(Long clubId) {
        User user = userReader.getCurrentUser();
        Club club = clubReader.findById(clubId);

        favoriteValidator.validateFavoriteExist(user, club);

        Favorite favorite = favoriteAppender.append(user, club);
        return FavoriteResponse.of(favorite, club, user);
    }

    @Transactional
    public FavoriteResponse deleteFavorite(Long clubId, Long favoriteId) {
        User user = userReader.getCurrentUser();
        Club club = clubReader.findById(clubId);
        Favorite favorite = favoriteReader.findById(favoriteId);

        favoriteValidator.validateDeleteFavorite(favorite, user.getId(), club.getId());

        favoriteAppender.delete(favorite);
        return FavoriteResponse.of(favorite, club, user);
    }

    @Transactional
    public void softDeleteByClubId(Long clubId) {
        favoriteAppender.softDeleteByClubId(clubId);
    }
}
