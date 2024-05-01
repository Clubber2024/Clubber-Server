package com.clubber.ClubberServer.domain.favorite.repository;

import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import java.util.List;

public interface FavoriteCustomRepository {
    List<Favorite> queryFavoritesByUserId(Long userId);
}
