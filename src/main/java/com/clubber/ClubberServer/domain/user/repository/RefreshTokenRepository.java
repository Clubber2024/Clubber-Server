package com.clubber.ClubberServer.domain.user.repository;

import com.clubber.ClubberServer.domain.admin.domain.AdminRefreshToken;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<AdminRefreshToken, Long> {

    Optional<AdminRefreshToken> findByRefreshToken(String refreshToken);

}
