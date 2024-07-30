package com.clubber.ClubberServer.domain.recruit.service;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.exception.AdminNotFoundException;
import com.clubber.ClubberServer.domain.admin.repository.AdminRepository;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.dto.GetClubPopularResponse;
import com.clubber.ClubberServer.domain.club.exception.ClubIdNotFoundException;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.dto.*;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitRepository;
import com.clubber.ClubberServer.global.config.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecruitService {

    private final AdminRepository adminRepository;
    private final ClubRepository clubRepository;
    private final RecruitRepository recruitRepository;

    @Transactional
    public PostRecruitPageResponse postRecruitsPage(PostRecruitPageRequest requestDTO){
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Admin admin = adminRepository.findById(currentUserId)
                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);

        Club club=admin.getClub();

        Recruit newRecruit=Recruit.of(club,requestDTO);

        recruitRepository.save(newRecruit);

        return PostRecruitPageResponse.of(newRecruit);

    }

    public GetRecruitsPageResponse getRecruitPageByClub(Long clubId){
        Club club=clubRepository.findById(clubId)
                .orElseThrow(()-> ClubIdNotFoundException.EXCEPTION);

        List<Recruit> recruits= recruitRepository.findByClubIdOrderByIdDesc(clubId);

        List<GetRecruitPageResponse> recruitsList= recruits.stream()
                .map(recruit -> GetRecruitPageResponse.from(recruit))
                .collect(Collectors.toList());

        return GetRecruitsPageResponse.of(club.getId(),recruitsList);

    }

    public GetAllRecruitsPageResponse getAllRecruitsPage(){
        List<Recruit> recruits=recruitRepository.findAll();
        List<GetRecruitPageResponse> recruitsList= recruits.stream()
                .map(recruit -> GetRecruitPageResponse.from(recruit))
                .collect(Collectors.toList());

        return GetAllRecruitsPageResponse.from(recruitsList);
    }


    public GetOneRecruitPageResponse getOneRecruitsPage(Long recruitId){
        Recruit recruit=recruitRepository.findById(recruitId)
                .orElse(()->)
        return GetOneRecruitPageResponse.from(recruit);

    }
}
