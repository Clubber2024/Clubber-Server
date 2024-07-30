package com.clubber.ClubberServer.domain.recruit.service;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.exception.AdminNotFoundException;
import com.clubber.ClubberServer.domain.admin.repository.AdminRepository;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.exception.ClubIdNotFoundException;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.dto.*;
import com.clubber.ClubberServer.domain.recruit.exception.RecruitNotFoundException;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitRepository;
import com.clubber.ClubberServer.global.config.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecruitService {

    private final AdminRepository adminRepository;
    private final ClubRepository clubRepository;
    private final RecruitRepository recruitRepository;

    @Transactional
    public PostRecruitResponse postRecruitsPage(PostRecruitRequest requestDTO){
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Admin admin = adminRepository.findById(currentUserId)
                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);

        Club club=admin.getClub();

        Recruit newRecruit=Recruit.of(club,requestDTO);

        recruitRepository.save(newRecruit);

        return PostRecruitResponse.of(newRecruit);

    }

    public GetRecruitsByClubIdResponse getRecruitsByClubId(Long clubId){
        Club club=clubRepository.findById(clubId)
                .orElseThrow(()-> ClubIdNotFoundException.EXCEPTION);

        List<Recruit> recruits= recruitRepository.findByClubIdOrderByIdDesc(clubId);

        List<GetRecruitResponse> recruitsList= recruits.stream()
                .map(recruit -> GetRecruitResponse.from(recruit))
                .collect(Collectors.toList());

        return GetRecruitsByClubIdResponse.of(club.getId(),recruitsList);

    }

    public GetAllRecruitsResponse getAllRecruitsPage(){
        List<Recruit> recruits=recruitRepository.findAll();
        List<GetRecruitResponse> recruitsList= recruits.stream()
                .map(recruit -> GetRecruitResponse.from(recruit))
                .collect(Collectors.toList());

        return GetAllRecruitsResponse.from(recruitsList);
    }


    public GetOneRecruitResponse getRecruitsByRecruitId(Long recruitId){
        Recruit recruit=recruitRepository.findById(recruitId)
                .orElseThrow(()-> RecruitNotFoundException.EXCEPTION);
        return GetOneRecruitResponse.from(recruit);
    }
}
