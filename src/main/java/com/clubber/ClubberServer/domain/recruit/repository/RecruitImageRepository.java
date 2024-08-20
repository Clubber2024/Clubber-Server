package com.clubber.ClubberServer.domain.recruit.repository;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitImageRepository extends JpaRepository<RecruitImage,Long> {
    List<RecruitImage> findByRecruit(Recruit recruit);

}
