package com.clubber.ClubberServer.domain.favorite.repository;

import java.util.List;

import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.domains.favorite.domain.Favorite;
import com.clubber.domain.domains.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> , FavoriteCustomRepository{

    boolean existsByUserAndClubAndIsDeleted(User user, Club club, boolean isDeleted);

    Optional<Favorite> findByIdAndIsDeleted(Long aLong, boolean isDeleted);

    List<Favorite> findAllByClub(Club club);
}
