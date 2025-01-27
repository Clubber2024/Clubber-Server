package com.clubber.ClubberServer.domain.recruit.repository;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitRepository extends JpaRepository<Recruit, Long>, RecruitCustomRepository {

    List<Recruit> findAllByClub(Club club);
}