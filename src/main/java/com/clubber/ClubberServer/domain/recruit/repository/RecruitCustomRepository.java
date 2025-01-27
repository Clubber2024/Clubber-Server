package com.clubber.ClubberServer.domain.recruit.repository;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RecruitCustomRepository {

    List<Recruit> queryTop5Recruits();

    Page<Recruit> queryRecruitsByClub(Club club, Pageable pageable);

    Page<Recruit> queryAllRecruits(Pageable pageable);

    Optional<Recruit> queryRecruitsById(Long recruitId);
}
