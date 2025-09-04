package com.clubber.ClubberInternal.domain.admin.repository;

import com.clubber.ClubberInternal.domain.admin.domain.PendingAdminInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PendingAdminInfoRepository extends JpaRepository<PendingAdminInfo, Long> {
    List<PendingAdminInfo> findByIsApproved(boolean isApproved);
}
