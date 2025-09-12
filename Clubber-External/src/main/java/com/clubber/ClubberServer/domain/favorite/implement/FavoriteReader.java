package com.clubber.ClubberServer.domain.favorite.implement;

import com.clubber.domain.domains.club.domain.Club;
import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.favorite.exception.FavoriteNotFoundException;
import com.clubber.ClubberServer.domain.favorite.repository.FavoriteRepository;
import com.clubber.ClubberServer.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavoriteReader {
    private final FavoriteRepository favoriteRepository;

    public boolean isFavoriteExist(User user, Club club) {
        return favoriteRepository.existsByUserAndClubAndIsDeleted(user, club, false);
    }

    public List<Favorite> findUserFavorites(Long userId) {
        return favoriteRepository.queryFavoritesByUserId(userId);
    }

    public Page<Favorite> findUserFavoritePages(Long userId, Pageable pageable) {
        return favoriteRepository.queryFavoritesPageByUserId(userId, pageable);
    }

    public Favorite findById(Long id) {
        return favoriteRepository.findByIdAndIsDeleted(id, false)
                .orElseThrow(() -> FavoriteNotFoundException.EXCEPTION);
    }
}
