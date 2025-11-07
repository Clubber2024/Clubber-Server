package com.clubber.domain.recruit.repository;

import com.clubber.domain.recruit.domain.Recruit;
import com.clubber.domain.recruit.domain.RecruitImage;

import java.util.List;

public interface RecruitImageCustomRepository {

    List<RecruitImage> queryRecruitImages(Recruit recruit);
}
