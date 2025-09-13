package com.clubber.domain.internal.repository;

import com.clubber.domain.internal.domain.InternalAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InternalAdminRepository extends JpaRepository<InternalAdmin, Long> {
    InternalAdmin findByUsername(String username);
}
