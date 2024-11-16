package com.clubber.ClubberServer.domain.recruit.repository;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.notice.domain.Notice;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecruitRepository extends JpaRepository<Recruit,Long>{

    @Query("SELECT COALESCE(MAX(id), 0) FROM Recruit")
    Long findMaxRecruitId();

    List<Recruit> findTop5ByOrderByIdDesc();

    @Query("SELECT r FROM Recruit r WHERE r.isDeleted = false AND r.club =:club ORDER BY r.id DESC ")
    Page<Recruit> findRecruitsByClub(@Param("club") Club club,Pageable pageable);

    @Query("SELECT r FROM Recruit r WHERE r.isDeleted = false ORDER BY r.id DESC ")
    Page<Recruit> findRecruits(Pageable pageable);

    Optional<Recruit> findRecruitById(@Param("recruitId") Long recruitId);

}
