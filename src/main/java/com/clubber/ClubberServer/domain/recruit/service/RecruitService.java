package com.clubber.ClubberServer.domain.recruit.service;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.exception.AdminNotFoundException;
import com.clubber.ClubberServer.domain.admin.repository.AdminRepository;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.exception.ClubIdNotFoundException;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitImage;
import com.clubber.ClubberServer.domain.recruit.dto.*;
import com.clubber.ClubberServer.domain.recruit.exception.RecruitNotFoundException;
import com.clubber.ClubberServer.domain.recruit.exception.RecruitUnauthorized;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitImageRepository;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitRepository;
import com.clubber.ClubberServer.global.config.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecruitService {

    private final AdminRepository adminRepository;
    private final ClubRepository clubRepository;
    private final RecruitRepository recruitRepository;
    private final RecruitImageRepository recruitImageRepository;

    @Transactional(readOnly = true)
    public GetAllRecruitsResponse getAllAdminRecruits(){
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Admin admin = adminRepository.findById(currentUserId)
                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);

        Club club=admin.getClub();

        List<Recruit> recruits = recruitRepository.findRecruitsWithImagesByClub(club)
                .orElseThrow(()->RecruitNotFoundException.EXCEPTION);

        List<GetOneRecruitResponse> recruitResponses = recruits.stream()
                .map(recruit -> {
                    List<String> imageUrls = recruit.getRecruitImages().stream()
                            .map(RecruitImage::getImageUrl)
                            .collect(Collectors.toList());
                    return GetOneRecruitResponse.of(recruit, imageUrls);
                })
                .collect(Collectors.toList());

        return GetAllRecruitsResponse.from(recruitResponses);

    }


    @Transactional
    public PostRecruitResponse postRecruitsPage(PostRecruitRequest requestDTO){
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Admin admin = adminRepository.findById(currentUserId)
                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);

        Club club=admin.getClub();

        Recruit newRecruit=Recruit.of(club,requestDTO);
        recruitRepository.save(newRecruit);

        for (String imageUrl : requestDTO.getImageUrl()) {
            recruitImageRepository.save(RecruitImage.of(imageUrl,newRecruit));
        }

        return PostRecruitResponse.of(newRecruit,requestDTO.getImageUrl());
    }


    @Transactional
    public DeleteRecruitByIdResponse deleteRecruitsById(Long recruitId){
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Admin admin = adminRepository.findById(currentUserId)
                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);

        Recruit recruit=recruitRepository.findRecruitWithImagesById(recruitId)
                .orElseThrow(()-> RecruitNotFoundException.EXCEPTION);


        if (recruit.getClub()!=admin.getClub()) {
            throw RecruitUnauthorized.EXCEPTION;
        }

        List<String> imageUrls = recruit.getRecruitImages().stream()
                .map(RecruitImage::getImageUrl)
                .collect(Collectors.toList());


        recruit.updateStatus();

        return DeleteRecruitByIdResponse.from(recruit,imageUrls);

    }

    @Transactional(readOnly = true)
    public GetRecruitsByClubIdResponse getRecruitsByClubId(Long clubId){
        Club club=clubRepository.findById(clubId)
                .orElseThrow(()-> ClubIdNotFoundException.EXCEPTION);

        List<Recruit> recruits = recruitRepository.findRecruitsWithImagesByClub(club)
                .orElseThrow(()->RecruitNotFoundException.EXCEPTION);

        List<GetOneRecruitResponse> recruitsList = recruits.stream()
                .map(recruit -> {
                    List<String> imageUrls = recruit.getRecruitImages().stream()
                            .map(RecruitImage::getImageUrl)
                            .collect(Collectors.toList());
                    return GetOneRecruitResponse.of(recruit, imageUrls);
                })
                .collect(Collectors.toList());

        return GetRecruitsByClubIdResponse.of(club.getId(),recruitsList);

    }

    @Transactional(readOnly = true)
    public GetAllRecruitsResponse getAllRecruitsPage(){
        List<Recruit> recruits = recruitRepository.findRecruitsWithImages()
                .orElseThrow(()->RecruitNotFoundException.EXCEPTION);

        List<GetOneRecruitResponse> recruitResponses = recruits.stream()
                .map(recruit -> {
                    List<String> imageUrls = recruit.getRecruitImages().stream()
                            .map(RecruitImage::getImageUrl)
                            .collect(Collectors.toList());
                    return GetOneRecruitResponse.of(recruit, imageUrls);
                })
                .collect(Collectors.toList());

        return GetAllRecruitsResponse.from(recruitResponses);
    }

    @Transactional
    public GetOneRecruitResponse getRecruitsByRecruitId(Long recruitId){

        Recruit recruit=recruitRepository.findRecruitWithImagesById(recruitId)
                .orElseThrow(()->RecruitNotFoundException.EXCEPTION);

        recruitRepository.incrementTotalView(recruitId);

        List<String> imageUrls = recruit.getRecruitImages().stream()
                .map(RecruitImage::getImageUrl)
                .collect(Collectors.toList());
        return GetOneRecruitResponse.of(recruit, imageUrls);

    }
}
