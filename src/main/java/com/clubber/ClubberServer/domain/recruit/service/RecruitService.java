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
import com.clubber.ClubberServer.domain.recruit.dto.mainPage.GetOneRecruitMainPageResponse;
import com.clubber.ClubberServer.domain.recruit.dto.mainPage.GetRecruitsMainPageResponse;
import com.clubber.ClubberServer.domain.recruit.dto.PostRecruitRequest;
import com.clubber.ClubberServer.domain.recruit.dto.PostRecruitResponse;
import com.clubber.ClubberServer.domain.recruit.exception.RecruitDeleteUnauthorized;
import com.clubber.ClubberServer.domain.recruit.exception.RecruitNotFoundException;
import com.clubber.ClubberServer.domain.recruit.exception.RecruitUnauthorized;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitImageRepository;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitRepository;
import com.clubber.ClubberServer.global.config.security.SecurityUtils;
import com.clubber.ClubberServer.global.page.PageResponse;
import com.clubber.ClubberServer.global.vo.ImageVO;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final RecruitImageRepository recruitImageRepository;



    @Transactional(readOnly = true)
    public PageResponse<GetOneRecruitResponse> getAllAdminRecruits(Pageable pageable){
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Admin admin = adminRepository.findById(currentUserId)
                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);

        Club club=admin.getClub();

        Page<Recruit> recruits = recruitRepository.findRecruitsWithImagesByClub(club,pageable);

        Page<GetOneRecruitResponse> recruitResponses = recruits.map(recruit -> {
            List<ImageVO> imageUrls = recruit.getRecruitImages().stream()
                    .map(RecruitImage::getImageUrl)
                    .collect(Collectors.toList());
            return GetOneRecruitResponse.of(recruit, imageUrls);
        });

        return PageResponse.of(recruitResponses);

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
            recruitImageRepository.save(RecruitImage.of(
                ImageVO.valueOf(imageUrl),newRecruit)
            );
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
            throw RecruitDeleteUnauthorized.EXCEPTION;
        }

        List<ImageVO> imageUrls = recruit.getRecruitImages().stream()
                .map(RecruitImage::getImageUrl)
                .collect(Collectors.toList());


        recruit.updateStatus();

        return DeleteRecruitByIdResponse.from(recruit,imageUrls);

    }



    @Transactional(readOnly = true)
    public PageResponse<GetOneRecruitResponse> getRecruitsByClubId(Long clubId,Pageable pageable){
        Club club=clubRepository.findById(clubId)
                .orElseThrow(()-> ClubIdNotFoundException.EXCEPTION);

        Page<Recruit> recruits = recruitRepository.findRecruitsWithImagesByClub(club,pageable);

        Page<GetOneRecruitResponse> recruitDto = recruits.map(recruit -> {
            List<ImageVO> imageUrls = recruit.getRecruitImages().stream()
                    .map(RecruitImage::getImageUrl)
                    .collect(Collectors.toList());
            return GetOneRecruitResponse.of(recruit, imageUrls);
        });

        return PageResponse.of(recruitDto);

    }



    @Transactional(readOnly = true)
    public GetRecruitsMainPageResponse getRecruitsMainPage(){
        List<Recruit> recruits = recruitRepository.findTop5ByOrderByIdDesc();

        if (recruits.isEmpty()){
            throw RecruitNotFoundException.EXCEPTION;
        }

        List <GetOneRecruitMainPageResponse> recruitsDto = recruits.stream()
                .map(recruit -> GetOneRecruitMainPageResponse.from(recruit))
                .collect(Collectors.toList());

        return GetRecruitsMainPageResponse.from(recruitsDto);
    }



    @Transactional(readOnly = true)
    public PageResponse<GetOneRecruitResponse> getAllRecruitsPage(Pageable pageable){
        Page<Recruit> recruits = recruitRepository.findRecruitsWithImages(pageable);

        Page<GetOneRecruitResponse> recruitDto = recruits.map(recruit -> {
            List<ImageVO> imageUrls = recruit.getRecruitImages().stream()
                    .map(RecruitImage::getImageUrl)
                    .collect(Collectors.toList());
            return GetOneRecruitResponse.of(recruit, imageUrls);
        });

        return PageResponse.of(recruitDto);

    }


    @Transactional
    public GetOneRecruitResponse getRecruitsByRecruitId(Long recruitId){

        Recruit recruit=recruitRepository.findRecruitWithImagesById(recruitId)
                .orElseThrow(()->RecruitNotFoundException.EXCEPTION);

        recruit.increaseTotalview();

        List<ImageVO> imageUrls = recruit.getRecruitImages().stream()
                .map(RecruitImage::getImageUrl)
                .collect(Collectors.toList());

        return GetOneRecruitResponse.of(recruit, imageUrls);

    }

    public GetOneRecruitResponse getOneAdminRecruitsById(Long recruitId){
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Admin admin = adminRepository.findById(currentUserId)
                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);

        Recruit recruit=recruitRepository.findRecruitWithImagesById(recruitId)
                .orElseThrow(()->RecruitNotFoundException.EXCEPTION);

        if (recruit.getClub()!=admin.getClub()) {
            throw RecruitUnauthorized.EXCEPTION;
        }

        List<ImageVO> imageUrls = recruit.getRecruitImages().stream()
                .map(RecruitImage::getImageUrl)
                .collect(Collectors.toList());

        return GetOneRecruitResponse.of(recruit, imageUrls);

    }
}
