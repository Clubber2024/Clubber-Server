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
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import static com.clubber.ClubberServer.global.jwt.JwtStatic.IMAGE_SERVER;

@Service
@RequiredArgsConstructor
public class RecruitService {

    private final AdminRepository adminRepository;
    private final ClubRepository clubRepository;
    private final RecruitRepository recruitRepository;
    private final RecruitImageRepository recruitImageRepository;



    @Transactional(readOnly = true)
    public PageResponse<GetOneRecruitInListResponse> getAllAdminRecruits(Pageable pageable){
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Admin admin = adminRepository.findById(currentUserId)
                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);

        Club club=admin.getClub();

        Page<Recruit> recruits = recruitRepository.findByIsDeletedFalseAndClubOrderByIdDesc(club,pageable);

        Page<GetOneRecruitInListResponse> recruitResponses = recruits.map(recruit -> {
            ImageVO imageUrl = recruit.getRecruitImages().stream()
                    .filter(recruitImage -> !recruitImage.isDeleted() && recruitImage.getOrderNum() == 1)
                    .map(RecruitImage::getImageUrl)
                    .findFirst()
                    .orElse(null);
            return GetOneRecruitInListResponse.of(recruit, imageUrl);
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

        AtomicLong order = new AtomicLong(1L);

        List<RecruitImage> savedImages = requestDTO.getImageKey().stream()
                .map(imageUrl -> {
                    RecruitImage recruitImage = recruitImageRepository.save(
                            RecruitImage.of(ImageVO.valueOf(imageUrl), newRecruit)
                    );
                    recruitImage.updateOrderNum(order.getAndIncrement()); // orderNum 설정
                    return recruitImage;
                })
                .collect(Collectors.toList());


        List<ImageVO> imageUrls = savedImages.stream()
                .sorted(Comparator.comparing(RecruitImage::getOrderNum))
                .map(RecruitImage::getImageUrl)
                .collect(Collectors.toList());

        return PostRecruitResponse.of(newRecruit,imageUrls);
    }


    @Transactional
    public DeleteRecruitByIdResponse deleteRecruitsById(Long recruitId){
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Admin admin = adminRepository.findById(currentUserId)
                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);

        Recruit recruit=recruitRepository.findByIdAndIsDeletedFalse(recruitId)
                .orElseThrow(()-> RecruitNotFoundException.EXCEPTION);

        if (recruit.getClub()!=admin.getClub()) {
            throw RecruitDeleteUnauthorized.EXCEPTION;
        }

        List<ImageVO> imageUrls = recruit.getRecruitImages().stream()
                .filter(recruitImage -> !recruitImage.isDeleted())
                .sorted(Comparator.comparing(RecruitImage::getOrderNum))
                .map(RecruitImage::getImageUrl)
                .collect(Collectors.toList());

        recruit.getRecruitImages().stream()
                .filter(recruitImage -> !recruitImage.isDeleted())
                .forEach(RecruitImage::updateStatus);

        recruit.delete();

        return DeleteRecruitByIdResponse.from(recruit,imageUrls);
    }


    @Transactional(readOnly = true)
    public PageResponse<GetOneRecruitResponse> getRecruitsByClubId(Long clubId,Pageable pageable){
        Club club=clubRepository.findById(clubId)
                .orElseThrow(()-> ClubIdNotFoundException.EXCEPTION);

        Page<Recruit> recruits = recruitRepository.findByIsDeletedFalseAndClubOrderByIdDesc(club,pageable);

        Page<GetOneRecruitResponse> recruitDto = recruits.map(recruit -> {
            List<ImageVO> imageUrls = recruit.getRecruitImages().stream()
                    .filter(recruitImage -> !recruitImage.isDeleted())
                    .sorted(Comparator.comparing(RecruitImage::getOrderNum))
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
    public PageResponse<GetOneRecruitInListResponse> getAllRecruitsPage(Pageable pageable){
        Page<Recruit> recruits = recruitRepository.findByIsDeletedFalseOrderByIdDesc(pageable);

        Page<GetOneRecruitInListResponse> recruitResponses = recruits.map(recruit -> {
            ImageVO imageUrl = recruit.getRecruitImages().stream()
                    .filter(recruitImage -> !recruitImage.isDeleted() && recruitImage.getOrderNum() == 1)
                    .map(RecruitImage::getImageUrl)
                    .findFirst()
                    .orElse(null);
            return GetOneRecruitInListResponse.of(recruit, imageUrl);
        });

        return PageResponse.of(recruitResponses);
    }


    @Transactional
    public GetOneRecruitWithClubResponse getRecruitsByRecruitId(Long recruitId){

        Recruit recruit=recruitRepository.findByIdAndIsDeletedFalse(recruitId)
                .orElseThrow(()->RecruitNotFoundException.EXCEPTION);

        recruit.increaseTotalview();

        List<ImageVO> imageUrls = recruit.getRecruitImages().stream()
                .filter(recruitImage -> !recruitImage.isDeleted())
                .sorted(Comparator.comparing(RecruitImage::getOrderNum))
                .map(RecruitImage::getImageUrl)
                .collect(Collectors.toList());

        return GetOneRecruitWithClubResponse.of(recruit, recruit.getClub(), imageUrls);
    }

    @Transactional(readOnly = true)
    public GetOneRecruitResponse getOneAdminRecruitsById(Long recruitId){
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Admin admin = adminRepository.findById(currentUserId)
                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);

        Recruit recruit=recruitRepository.findByIdAndIsDeletedFalse(recruitId)
                .orElseThrow(()->RecruitNotFoundException.EXCEPTION);

        if (recruit.getClub()!=admin.getClub()) {
            throw RecruitUnauthorized.EXCEPTION;
        }

        List<ImageVO> imageUrls = recruit.getRecruitImages().stream()
                .filter(recruitImage -> !recruitImage.isDeleted())
                .sorted(Comparator.comparing(RecruitImage::getOrderNum))
                .map(RecruitImage::getImageUrl)
                .collect(Collectors.toList());

        return GetOneRecruitResponse.of(recruit, imageUrls);
    }

    @Transactional
    public UpdateRecruitResponse changeAdminRecruits(Long recruitId,UpdateRecruitRequest requestPage){

        Long currentUserId = SecurityUtils.getCurrentUserId();

        Admin admin = adminRepository.findById(currentUserId)
                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);

        Recruit recruit=recruitRepository.findByIdAndIsDeletedFalse(recruitId)
                .orElseThrow(()->RecruitNotFoundException.EXCEPTION);

        if (recruit.getClub()!=admin.getClub()) {
            throw RecruitUnauthorized.EXCEPTION;
        }

        recruit.updateRecruitPage(requestPage.getTitle(), requestPage.getContent());

        List<RecruitImage> recruitImages = recruit.getRecruitImages().stream() // 현재 해당 게시글의 모든 이미지 조회
                .filter(recruitImage -> !recruitImage.isDeleted())
                .collect(Collectors.toList());


        recruitImages.stream()
                .filter(recruitImage -> requestPage.getDeletedImageUrls().stream()
                        .anyMatch(deleteImage -> deleteImage.substring(IMAGE_SERVER.length()).equals(recruitImage.getImageUrl().getImageUrl())))
                .forEach(RecruitImage::updateStatus);


        List<RecruitImage> newImages = requestPage.getNewImageKeys().stream() // 추가 요청 들어온것들은 recruitImage객체 생성하여 저장
                .map(imageKey -> recruitImageRepository.save(
                        RecruitImage.of(ImageVO.valueOf(imageKey), recruit))
                )
                .collect(Collectors.toList());


        List<RecruitImage> revisedRecruitImages = recruitImageRepository.findByRecruitAndIsDeletedFalse(recruit);

        AtomicLong order = new AtomicLong(1L);

        List<String> finalImages = requestPage.getImages();
        for (String image : finalImages){
            if (image.startsWith(IMAGE_SERVER)){
                revisedRecruitImages.stream()
                        .filter(recruitImage-> recruitImage.getImageUrl().getImageUrl().equals(image.substring(IMAGE_SERVER.length())))
                        .forEach(recruitImage -> recruitImage.updateOrderNum(order.getAndIncrement()));
            }
            else{
                newImages.stream()
                        .filter(recruitImage-> recruitImage.getImageUrl().getImageUrl().equals(image))
                        .forEach(recruitImage -> recruitImage.updateOrderNum(order.getAndIncrement()));
            }
        }

        return UpdateRecruitResponse.of(recruit,requestPage.getImages());
    }
}