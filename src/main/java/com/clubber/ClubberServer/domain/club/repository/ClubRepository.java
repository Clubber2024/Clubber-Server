package com.clubber.ClubberServer.domain.club.repository;

import com.clubber.ClubberServer.domain.club.domain.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClubRepository extends JpaRepository<Club, Long> {
    List<Club> findByClubType(String clubType);

    List<Club> findByClubTypeAndDivision(String clubType, String division);

    List<Club> findByCollege(String college);

    List<Club> findByDepartment(String department);

    List<Club> findByHashtag(String hashtag);

    Optional<Club> findByName(String clubName);
}
