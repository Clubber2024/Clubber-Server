package com.clubber.domain.domains.admin.repository;

import com.clubber.domain.domains.admin.domain.PendingAdminInfo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PendingAdminInfoRepository extends JpaRepository<PendingAdminInfo, Long> {

	boolean existsPendingAdminInfoByClubNameAndIsApproved(String name, boolean isApproved);

	Optional<PendingAdminInfo> findByUsername(String username);

	List<PendingAdminInfo> findByIsApproved(boolean isApproved);
}
