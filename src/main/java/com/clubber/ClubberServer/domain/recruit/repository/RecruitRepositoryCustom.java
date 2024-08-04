package com.clubber.ClubberServer.domain.recruit.repository;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;

import java.util.List;

public interface RecruitRepositoryCustom {
    List<Recruit> findRecruitsWithImagesByClubId(Long clubId);
}
