package com.clubber.domain.domains.admin.repository;


import com.clubber.domain.domains.admin.domain.Admin;
import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.domains.user.domain.AccountState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByUsernameAndAccountState(String username, AccountState accountState);

    Optional<Admin> findAdminByIdAndAccountState(Long id, AccountState accountState);

    Optional<Admin> findByEmailAndAccountState(String email, AccountState accountState);

    Optional<Admin> findByEmailAndClubIdAndAccountState(String email, Long clubId, AccountState accountState);

    Optional<Admin> findByClubAndAccountState(Club club, AccountState accountState);

    boolean existsByUsernameAndAccountState(String username, AccountState accountState);

    boolean existsByEmailAndUsernameAndAccountState(String email, String username, AccountState accountState);

    boolean existsByEmailAndClubIdAndAccountState(String email, Long clubId, AccountState accountState);
}
