package com.clubber.ClubberServer.domain.recruit.repository;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecruitRepository extends JpaRepository<Recruit,Long>{

    @Query("SELECT r FROM Recruit r LEFT JOIN FETCH r.recruitImages ORDER BY r.id DESC")
    List<Recruit> findRecruitsWithImages();

    @Query("SELECT r FROM Recruit r LEFT JOIN FETCH r.recruitImages WHERE r.club = :club ORDER BY r.id DESC")
    List<Recruit> findRecruitsWithImagesByClub(@Param("club") Club club);

    @Query("SELECT r FROM Recruit r LEFT JOIN FETCH r.recruitImages WHERE r.id = :recruitId ORDER BY r.id DESC")
    Recruit findRecruitWithImagesById(@Param("recruitId") Long recruitId);

    @Modifying
    @Query("UPDATE Recruit r SET r.totalView = r.totalView + 1 WHERE r.id = :id")
    void incrementTotalView(@Param("id") Long id);


}
