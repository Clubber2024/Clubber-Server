package com.clubber.ClubberInternal.domain.admin.repository;

import com.clubber.ClubberInternal.domain.admin.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
