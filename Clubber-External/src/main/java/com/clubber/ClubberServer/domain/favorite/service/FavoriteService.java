package com.clubber.ClubberServer.domain.favorite.service;

import com.clubber.domain.domains.club.domain.Club;
import com.clubber.ClubberServer.domain.club.implement.ClubReader;
import com.clubber.domain.domains.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.favorite.dto.FavoriteResponse;
import com.clubber.ClubberServer.domain.favorite.implement.FavoriteAppender;
import com.clubber.ClubberServer.domain.favorite.implement.FavoriteReader;
import com.clubber.ClubberServer.domain.favorite.implement.FavoriteValidator;
import com.clubber.domain.domains.user.domain.User;
import com.clubber.ClubberServer.domain.user.implement.UserReader;
import com.clubber.ClubberServer.global.config.security.SecurityUtils;
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
        return FavoriteResponse.of(favorite, club.getId(), user.getId());
    }

    @Transactional
    public FavoriteResponse deleteFavorite(Long clubId, Long favoriteId) {
        Favorite favorite = favoriteReader.findById(favoriteId);
        Long userId = SecurityUtils.getCurrentUserId();
        favoriteAppender.delete(favorite, userId, clubId);
        return FavoriteResponse.of(favorite, clubId, userId);
    }

    @Transactional
    public void softDeleteByClubId(Long clubId) {
        favoriteAppender.softDeleteByClubId(clubId);
    }
}
