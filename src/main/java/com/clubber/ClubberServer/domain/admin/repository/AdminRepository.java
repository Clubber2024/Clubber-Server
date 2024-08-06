package com.clubber.ClubberServer.domain.admin.repository;


import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.user.domain.AccountState;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long>{

    Optional<Admin> findByUsernameAndAccountState(String username, AccountState accountState);
}
