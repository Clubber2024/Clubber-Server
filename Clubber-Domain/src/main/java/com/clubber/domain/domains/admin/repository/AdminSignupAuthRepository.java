package com.clubber.domain.domains.admin.repository;

import com.clubber.domain.domains.admin.domain.AdminSignupAuth;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface AdminSignupAuthRepository extends CrudRepository<AdminSignupAuth, String> {

	Optional<AdminSignupAuth> findByEmailAndAuthCode(String email, String authRandomString);
}
