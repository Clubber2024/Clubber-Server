package com.clubber.ClubberServer.domain.admin.repository;

import com.clubber.ClubberServer.domain.admin.domain.Admin;

public interface AdminCustomRepository {
    Admin queryAdminById(Long adminId);
}
