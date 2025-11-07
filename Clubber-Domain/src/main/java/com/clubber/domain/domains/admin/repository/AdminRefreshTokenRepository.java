package com.clubber.domain.domains.admin.repository;

import com.clubber.domain.domains.admin.domain.AdminRefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AdminRefreshTokenRepository extends CrudRepository<AdminRefreshToken, Long> {
    Optional<AdminRefreshToken> findByRefreshToken(String refreshToken);
}
