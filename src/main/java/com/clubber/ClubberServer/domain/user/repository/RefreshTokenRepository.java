package com.clubber.ClubberServer.domain.user.repository;

import com.clubber.ClubberServer.domain.user.domain.RefreshTokenEntity;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshTokenEntity, Long> {

}
