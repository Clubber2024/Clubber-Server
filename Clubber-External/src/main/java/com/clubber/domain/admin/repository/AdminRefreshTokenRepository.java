package com.clubber.domain.admin.repository;

import com.clubber.domain.admin.domain.AdminRefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AdminRefreshTokenRepository extends CrudRepository<AdminRefreshToken, Long> {
    Optional<AdminRefreshToken> findByRefreshToken(String refreshToken);
}
