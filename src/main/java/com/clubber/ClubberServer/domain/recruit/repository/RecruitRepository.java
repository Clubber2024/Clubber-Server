package com.clubber.ClubberServer.domain.recruit.repository;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecruitRepository extends JpaRepository<Recruit,Long>{


    @Query("SELECT r FROM Recruit r LEFT JOIN FETCH r.recruitImages WHERE r.isDeleted = false ORDER BY r.id DESC")
    Optional<List<Recruit>> findRecruitsWithImages();

    @Query("SELECT r FROM Recruit r LEFT JOIN FETCH r.recruitImages WHERE r.club = :club AND r.isDeleted = false ORDER BY r.id DESC")
    Optional<List<Recruit>> findRecruitsWithImagesByClub(@Param("club") Club club);

    @Query("SELECT r FROM Recruit r LEFT JOIN FETCH r.recruitImages WHERE r.id = :recruitId AND r.isDeleted = false ORDER BY r.id DESC")
    Optional<Recruit> findRecruitWithImagesById(@Param("recruitId") Long recruitId);

    @Modifying
    @Query("UPDATE Recruit r SET r.totalView = r.totalView + 1 WHERE r.id = :id AND r.isDeleted = false")
    void incrementTotalView(@Param("id") Long id);

    @Query("SELECT COALESCE(MAX(id), 0) FROM Recruit")
    Long findMaxRecruitId();

}
