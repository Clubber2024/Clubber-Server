package com.clubber.ClubberServer.domain.recruit.service;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.implement.AdminReader;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.implement.ClubReader;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitImage;
import com.clubber.ClubberServer.domain.recruit.dto.*;
import com.clubber.ClubberServer.domain.recruit.dto.mainPage.GetRecruitsMainPageResponse;
import com.clubber.ClubberServer.domain.recruit.exception.RecruitImageDeleteRemainDuplicatedException;
import com.clubber.ClubberServer.domain.recruit.exception.RecruitImageNotFoundException;
import com.clubber.ClubberServer.domain.recruit.exception.RecruitImageRevisedFinalSizeException;
import com.clubber.ClubberServer.domain.recruit.exception.RecruitNotFoundException;
import com.clubber.ClubberServer.domain.recruit.implement.RecruitAppender;
import com.clubber.ClubberServer.domain.recruit.implement.RecruitImageAppender;
import com.clubber.ClubberServer.domain.recruit.implement.RecruitReader;
import com.clubber.ClubberServer.domain.recruit.implement.RecruitValidator;
import com.clubber.ClubberServer.domain.recruit.mapper.RecruitMapper;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitImageRepository;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitRepository;
import com.clubber.ClubberServer.global.common.page.PageResponse;
import com.clubber.ClubberServer.global.vo.image.ImageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static com.clubber.ClubberServer.global.common.consts.ClubberStatic.IMAGE_SERVER;

@Service
@RequiredArgsConstructor
public class RecruitService {

    private final AdminReader adminReader;
    private final ClubReader clubReader;
    private final RecruitRepository recruitRepository;
    private final RecruitReader recruitReader;
    private final RecruitAppender recruitAppender;
    private final RecruitValidator recruitValidator;
    private final RecruitImageAppender recruitImageAppender;
    private final RecruitImageRepository recruitImageRepository;
    private final RecruitMapper recruitMapper;

    @Transactional(readOnly = true)
    public PageResponse<GetOneRecruitInListResponse> getAllAdminRecruits(Pageable pageable) {
        Admin admin = adminReader.getCurrentAdmin();
        Club club = admin.getClub();

        Page<Recruit> recruits = recruitRepository.queryRecruitsByClub(club,
            pageable);

        return recruitMapper.getRecruitsPageResponse(recruits);
    }

    @Transactional
    public PostRecruitResponse postRecruitsPage(PostRecruitRequest request) {
        Admin admin = adminReader.getCurrentAdmin();
        Club club = admin.getClub();

        Recruit newRecruit = request.toEntity(club);
        recruitRepository.save(newRecruit);

        recruitImageAppender.appendRecruitImages(request.getImageKey(), newRecruit);
        return recruitMapper.getRecruitWithImageUrls(newRecruit, savedImages);
    }

    @Transactional
    public DeleteRecruitByIdResponse deleteRecruitsById(Long recruitId) {
        Admin admin = adminReader.getCurrentAdmin();
        Recruit recruit = recruitReader.findRecruitById(recruitId);

        recruitValidator.validateRecruitClub(recruit, admin);

        recruitAppender.delete(recruit);
        recruitImageAppender.deleteRecruitImages(recruit.getRecruitImages());

        List<ImageVO> imageUrls = recruitMapper.getDeletedRecruitImages(recruit);
        return DeleteRecruitByIdResponse.from(recruit, imageUrls);
    }

    @Transactional(readOnly = true)
    public PageResponse<GetOneRecruitInListResponse> getRecruitsByClubId(Long clubId,
        Pageable pageable) {
        Club club = clubReader.findById(clubId);

        Page<Recruit> recruits = recruitRepository.queryRecruitsByClub(club,
            pageable);

        return recruitMapper.getRecruitsPageResponse(recruits);
    }

    @Transactional(readOnly = true)
    public GetRecruitsMainPageResponse getRecruitsMainPage() {
        List<Recruit> recruits = recruitRepository.queryTop5Recruits();

        if (recruits.isEmpty()) {
            throw RecruitNotFoundException.EXCEPTION;
        }

        return recruitMapper.getRecruitsMainPage(recruits);
    }

    @Transactional(readOnly = true)
    public PageResponse<GetOneRecruitInListResponse> getAllRecruitsPage(Pageable pageable) {
        Page<Recruit> recruits = recruitRepository.queryAllRecruits(pageable);

        return recruitMapper.getRecruitsPageResponse(recruits);
    }

    @Transactional
    public GetOneRecruitWithClubResponse getRecruitsByRecruitId(Long recruitId) {
        Recruit recruit = recruitReader.findRecruitById(recruitId);
        recruitAppender.increaseTotalView(recruit);
        return recruitMapper.getRecruitsByRecruitId(recruit);
    }

    @Transactional(readOnly = true)
    public GetOneRecruitResponse getOneAdminRecruitsById(Long recruitId) {
        Admin admin = adminReader.getCurrentAdmin();
        Recruit recruit = recruitReader.findRecruitById(recruitId);
        recruitValidator.validateRecruitClub(recruit, admin);
        return recruitMapper.getOneAdminRecruitsById(recruit);
    }

    @Transactional
    public UpdateRecruitResponse changeAdminRecruits(Long recruitId,
        UpdateRecruitRequest requestPage) {
        Admin admin = adminReader.getCurrentAdmin();
        Recruit recruit = recruitReader.findRecruitById(recruitId);
        recruitValidator.validateRecruitClub(recruit, admin);

        recruit.updateRecruitPage(requestPage.getTitle(), requestPage.getContent(),
            requestPage.getEverytimeUrl());

        // 기존 모집글의 모든 이미지 조회
        List<RecruitImage> recruitImages = recruit.getRecruitImages().stream()
            .filter(recruitImage -> !recruitImage.isDeleted())
            .collect(Collectors.toList());

        Set<String> existingImageUrls = recruitImages.stream()
            .map(img -> img.getImageUrl().getImageUrl())
            .collect(Collectors.toSet());

        // 삭제 요청된 이미지 Set 으로 변환
        Set<String> deletedImageUrls = requestPage.getDeletedImageUrls().stream()
            .map(deleteImage -> deleteImage.substring(IMAGE_SERVER.length()))
            .collect(Collectors.toSet());

        // 유지하는 이미지 Set 으로 변환
        Set<String> remainImageUrls = requestPage.getRemainImageUrls().stream()
            .map(remainImage -> remainImage.substring(IMAGE_SERVER.length()))
            .collect(Collectors.toSet());

        // 삭제 요청된 이미지가 기존 이미지에 있는지 확인 ( 없으면 예외 처리  &  있으면 삭제 처리 )
        deletedImageUrls.forEach(deleteImage -> {
            if (!existingImageUrls.contains(deleteImage)) {
                throw RecruitImageNotFoundException.EXCEPTION; // 존재하지 않는 경우 예외 발생
            }
            recruitImages.stream()
                .filter(
                    recruitImage -> recruitImage.getImageUrl().getImageUrl().equals(deleteImage))
                .forEach(RecruitImage::delete); // 존재하는 경우 상태 업데이트
        });

        // 유지해야 하는 이미지가 삭제 요청 목록에 포함되었는지 확인
        if (!Collections.disjoint(remainImageUrls, deletedImageUrls)) {
            throw RecruitImageDeleteRemainDuplicatedException.EXCEPTION;
        }

        // 유지해야 하는 이미지가 기존 모집글에 존재하는지 확인
        remainImageUrls.forEach(remainImage -> {
            if (!existingImageUrls.contains(remainImage)) {
                throw RecruitImageNotFoundException.EXCEPTION;
            }
        });

        // 추가된 이미지 저장
        List<RecruitImage> newImages = requestPage.getNewImageKeys().stream()
            .map(imageKey -> recruitImageRepository.save(
                RecruitImage.of(ImageVO.valueOf(imageKey), recruit))
            )
            .collect(Collectors.toList());

        List<RecruitImage> revisedRecruitImages = recruitImageRepository.queryRecruitImages(
            recruit);

        // 이미지 저장 순서 처리
        AtomicLong order = new AtomicLong(1L);
        List<String> finalImages = requestPage.getImages();

        if (finalImages.size() != revisedRecruitImages.size()) {
            throw RecruitImageRevisedFinalSizeException.EXCEPTION;
        }

        // revisedRecruitImages를 Map으로 변환
        Map<String, RecruitImage> revisedImageMap = revisedRecruitImages.stream()
            .collect(Collectors.toMap(img -> img.getImageUrl().getImageUrl(), img -> img));

        for (String image : finalImages) {
            RecruitImage recruitImage;

            if (image.startsWith(IMAGE_SERVER)) {
                recruitImage = revisedImageMap.get(image.substring(IMAGE_SERVER.length()));
            } else {
                recruitImage = revisedImageMap.get(image);
            }

            if (recruitImage == null) {
                throw RecruitImageNotFoundException.EXCEPTION;
            }
            recruitImage.updateOrderNum(order.getAndIncrement());
        }
        return UpdateRecruitResponse.of(recruit, requestPage.getImages());

    }

    @Transactional
    public void softDeleteByClubId(Long clubId) {
        recruitRepository.softDeleteRecruitByClubId(clubId);
    }
}