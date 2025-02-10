package com.clubber.ClubberServer.domain.recruit.repository;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitImage;

import java.util.List;

public interface RecruitImageCustomRepository {

    List<RecruitImage> queryRecruitImages(Recruit recruit);
}
