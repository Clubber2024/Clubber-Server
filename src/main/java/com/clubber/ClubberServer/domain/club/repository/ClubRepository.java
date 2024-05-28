package com.clubber.ClubberServer.domain.club.repository;

import com.clubber.ClubberServer.domain.club.domain.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface ClubRepository extends JpaRepository<Club, Long> {

    List<Club> findByDivision(String division);

    List<Club> findByDepartment(String department);

    List<Club> findByHashtag(String hashtag);


    @Query("SELECT c FROM Club c JOIN FETCH c.clubInfo WHERE c.name LIKE %:name% ORDER BY CASE WHEN c.clubType = 'center' THEN 0 WHEN c.clubType = 'small' THEN 1 ELSE 2 END")
    List<Club> findByName(String name);

    @Query("SELECT c FROM Club c JOIN FETCH c.clubInfo ORDER BY c.clubInfo.totalView DESC")
    List<Club> findTop10ByOrderByClubInfoTotalViewDesc(Pageable pageable);



}
