package com.clubber.ClubberServer.domain.admin.repository;

import com.clubber.ClubberServer.domain.admin.domain.UserRefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface UserRefreshTokenRepository extends CrudRepository<UserRefreshToken, Long> {
}
