package com.clubber.ClubberServer.domain.favorite.repository;

import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> , FavoriteCustomRepository{

}
