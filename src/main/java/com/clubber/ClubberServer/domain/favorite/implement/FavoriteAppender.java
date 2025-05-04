package com.clubber.ClubberServer.domain.favorite.implement;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.favorite.repository.FavoriteRepository;
import com.clubber.ClubberServer.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FavoriteAppender {
    private final FavoriteRepository favoriteRepository;

    public Favorite append(User user, Club club) {
        Favorite favorite = Favorite.from(user, club);
        return favoriteRepository.save(favorite);
    }

    public void delete(Favorite favorite) {
        favorite.delete();
    }

    public void softDeleteByClubId(Long clubId) {
        favoriteRepository.softDeleteFavoriteByClubId(clubId);
    }
}
