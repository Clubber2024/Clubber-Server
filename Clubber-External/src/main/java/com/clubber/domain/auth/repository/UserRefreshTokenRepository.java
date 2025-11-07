package com.clubber.domain.auth.repository;

import com.clubber.domain.auth.domain.UserRefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRefreshTokenRepository extends CrudRepository<UserRefreshToken, Long> {
    Optional<UserRefreshToken> findByRefreshToken(String refreshToken);
}
