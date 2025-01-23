package com.clubber.ClubberServer.domain.favorite.repository;

import java.util.List;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> , FavoriteCustomRepository{

    boolean existsByUserAndClubAndIsDeleted(User user, Club club, boolean isDeleted);

    Optional<Favorite> findByIdAndIsDeleted(Long aLong, boolean isDeleted);

    List<Favorite> findAllByClub(Club club);
}
