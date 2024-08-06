package com.clubber.ClubberServer.domain.favorite.repository;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> , FavoriteCustomRepository{

    boolean existsByUserAndClubAndIsDeleted(User user, Club club, boolean isDeleted);
}
