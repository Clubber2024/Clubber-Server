package com.clubber.ClubberServer.domain.recruit.repository;

import com.clubber.ClubberServer.domain.recruit.domain.RecruitImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitImageRepository extends JpaRepository<RecruitImage, Long>,
    RecruitImageCustomRepository {

}
