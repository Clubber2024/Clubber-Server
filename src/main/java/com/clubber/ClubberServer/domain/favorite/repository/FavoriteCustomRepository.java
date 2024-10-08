package com.clubber.ClubberServer.domain.favorite.repository;

import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FavoriteCustomRepository {
    List<Favorite> queryFavoritesByUserId(Long userId);

    Page<Favorite> queryFavoritesPageByUserId(Long userId, Pageable pageable);
}
