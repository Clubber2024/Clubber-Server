package com.clubber.ClubberServer.domain.admin.repository;

import com.clubber.ClubberServer.domain.admin.domain.PendingAdminInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PendingAdminInfoRepository extends JpaRepository<PendingAdminInfo, Long> {

}
