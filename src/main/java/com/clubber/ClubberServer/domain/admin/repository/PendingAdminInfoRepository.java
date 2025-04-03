package com.clubber.ClubberServer.domain.admin.repository;

import com.clubber.ClubberServer.domain.admin.domain.PendingAdminInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PendingAdminInfoRepository extends JpaRepository<PendingAdminInfo, Long> {

	boolean existsByClubNameAndApproved(String name, boolean isApproved);
}
