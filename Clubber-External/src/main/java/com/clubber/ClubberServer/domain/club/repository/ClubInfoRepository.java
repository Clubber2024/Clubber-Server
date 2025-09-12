package com.clubber.ClubberServer.domain.club.repository;

import com.clubber.domain.domains.club.domain.ClubInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubInfoRepository extends JpaRepository<ClubInfo, Long> {
}
