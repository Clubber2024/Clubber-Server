package com.clubber.ClubberServer.domain.admin.repository;

import com.clubber.ClubberServer.domain.admin.domain.AdminEmailAuth;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface AdminEmailAuthRepository extends CrudRepository<AdminEmailAuth, Long> {

	Optional<AdminEmailAuth> findByEmailAndAuthRandomString(String adminEmail, String authRandomString);
}
