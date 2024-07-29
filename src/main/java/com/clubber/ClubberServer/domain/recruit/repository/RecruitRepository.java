package com.clubber.ClubberServer.domain.recruit.repository;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitRepository extends JpaRepository<Recruit,Long> {
}
