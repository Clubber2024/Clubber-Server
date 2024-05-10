package com.clubber.ClubberServer.domain.user.repository;

import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.domain.UserStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserBySnsId(Long id);
    Optional<User> findByIdAndUserStatus(Long id, UserStatus userStatus);
}
