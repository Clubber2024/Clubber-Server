package com.clubber.ClubberServer.domain.admin.repository;


import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.user.domain.AccountState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByUsernameAndAccountState(String username, AccountState accountState);

    Optional<Admin> findAdminByIdAndAccountState(Long id, AccountState accountState);

    Optional<Admin> findByEmailAndAccountState(String email, AccountState accountState);

    Optional<Admin> findByEmailAndClubIdAndAccountState(String email, Long clubId, AccountState accountState);

    boolean existsByUsernameAndAccountState(String username, AccountState accountState);

    boolean existsByEmailAndAccountState(String email, AccountState accountState);

    boolean existsByEmailAndClubIdAndAccountState(String email, Long clubId, AccountState accountState);
}
