package com.clubber.ClubberServer.domain.favorite.implement;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.favorite.repository.FavoriteRepository;
import com.clubber.ClubberServer.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavoriteReader {
    private final FavoriteRepository favoriteRepository;

    public boolean isFavoriteExist(User user, Club club) {
        return favoriteRepository.existsByUserAndClubAndIsDeleted(user, club, false);
    }
}
