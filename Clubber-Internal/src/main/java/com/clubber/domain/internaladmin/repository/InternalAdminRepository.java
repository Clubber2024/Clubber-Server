package com.clubber.domain.internaladmin.repository;

import com.clubber.domain.internaladmin.domain.InternalAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InternalAdminRepository extends JpaRepository<InternalAdmin, Long> {
    InternalAdmin findByUsername(String username);
}
