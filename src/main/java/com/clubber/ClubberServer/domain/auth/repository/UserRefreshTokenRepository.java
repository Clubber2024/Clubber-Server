package com.clubber.ClubberServer.domain.auth.repository;

import com.clubber.ClubberServer.domain.auth.domain.UserRefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface UserRefreshTokenRepository extends CrudRepository<UserRefreshToken, Long> {
}
