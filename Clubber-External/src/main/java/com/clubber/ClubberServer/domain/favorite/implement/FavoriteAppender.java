package com.clubber.ClubberServer.domain.favorite.implement;

import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.domains.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.favorite.repository.FavoriteRepository;
import com.clubber.domain.domains.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FavoriteAppender {
    private final FavoriteRepository favoriteRepository;

    public Favorite append(User user, Club club) {
        Favorite favorite = Favorite.create(user, club);
        return favoriteRepository.save(favorite);
    }

    public void delete(Favorite favorite, Long userId, Long clubId) {
        favorite.delete(userId, clubId);
    }

    public void softDeleteByClubId(Long clubId) {
        favoriteRepository.softDeleteFavoriteByClubId(clubId);
    }
}
