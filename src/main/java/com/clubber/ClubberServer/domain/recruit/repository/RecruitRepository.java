package com.clubber.ClubberServer.domain.recruit.repository;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RecruitRepository extends JpaRepository<Recruit, Long>, RecruitCustomRepository {

    @Query("SELECT COALESCE(MAX(id), 0) FROM Recruit")
    Long findMaxRecruitId();

    List<Recruit> findAllByClub(Club club);
}