package com.clubber.ClubberServer.domain.recruit.repository;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecruitRepository extends JpaRepository<Recruit,Long>,RecruitRepositoryCustom{
    List<Recruit> findByClubIdOrderByIdDesc(Long clubId);

    @Modifying
    @Query("UPDATE Recruit r SET r.deleted = true WHERE r.id = :id")
    void softDeleteRecruit(@Param("id") Long id);

//    @Query("SELECT r, ri FROM Recruit r LEFT JOIN RecruitImage ri ON r.id = ri.recruit.id WHERE r.club.id = :clubId AND r.deleted = false")
//    List<Object[]> findRecruitsWithImagesByClubId(@Param("clubId") Long clubId);

}
