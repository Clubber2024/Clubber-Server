package com.clubber.ClubberServer.domain.club.repository;

import com.clubber.ClubberServer.domain.club.domain.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface ClubRepository extends JpaRepository<Club, Long> {
    List<Club> findByClubType(String clubType);

    List<Club> findByClubTypeAndDivision(String clubType, String division);

    List<Club> findByCollege(String college);

    List<Club> findByDepartment(String department);

    List<Club> findByHashtag(String hashtag);

    //@Query("SELECT c FROM Club c WHERE LOWER(REPLACE(c.name, ' ', '')) = LOWER(REPLACE(:name, ' ', ''))")
    Optional<Club> findByName(String clubName);

    @Query("SELECT c FROM Club c JOIN c.clubInfo ci ORDER BY ci.totalView DESC")
    Page<Club> findTopClubsByTotalView(Pageable pageable);
}
