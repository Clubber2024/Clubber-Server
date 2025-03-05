package com.clubber.ClubberServer.domain.admin.repository;

import com.clubber.ClubberServer.domain.admin.domain.AdminSignupAuth;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface AdminEmailAuthRepository extends CrudRepository<AdminSignupAuth, String> {

	Optional<AdminSignupAuth> findByEmailAndAuthCode(String email, String authRandomString);
}
