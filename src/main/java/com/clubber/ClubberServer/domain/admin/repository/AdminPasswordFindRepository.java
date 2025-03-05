package com.clubber.ClubberServer.domain.admin.repository;

import com.clubber.ClubberServer.domain.admin.domain.AdminPasswordFindAuth;
import org.springframework.data.repository.CrudRepository;

public interface AdminPasswordFindRepository extends CrudRepository<AdminPasswordFindAuth, String> {
}
