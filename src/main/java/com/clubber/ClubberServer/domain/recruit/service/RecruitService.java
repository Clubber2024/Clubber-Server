package com.clubber.ClubberServer.domain.recruit.service;

import static com.clubber.ClubberServer.global.common.consts.ClubberStatic.IMAGE_SERVER;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.service.AdminReadService;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.exception.ClubIdNotFoundException;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitImage;
import com.clubber.ClubberServer.domain.recruit.dto.DeleteRecruitByIdResponse;
import com.clubber.ClubberServer.domain.recruit.dto.GetOneRecruitInListResponse;
import com.clubber.ClubberServer.domain.recruit.dto.GetOneRecruitResponse;
import com.clubber.ClubberServer.domain.recruit.dto.GetOneRecruitWithClubResponse;
import com.clubber.ClubberServer.domain.recruit.dto.PostRecruitRequest;
import com.clubber.ClubberServer.domain.recruit.dto.PostRecruitResponse;
import com.clubber.ClubberServer.domain.recruit.dto.UpdateRecruitRequest;
import com.clubber.ClubberServer.domain.recruit.dto.UpdateRecruitResponse;
import com.clubber.ClubberServer.domain.recruit.dto.mainPage.GetRecruitsMainPageResponse;
import com.clubber.ClubberServer.domain.recruit.exception.RecruitDeleteUnauthorized;
import com.clubber.ClubberServer.domain.recruit.exception.RecruitNotFoundException;
import com.clubber.ClubberServer.domain.recruit.exception.RecruitUnauthorized;
import com.clubber.ClubberServer.domain.recruit.mapper.RecruitMapper;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitImageRepository;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitRepository;
import com.clubber.ClubberServer.global.common.page.PageResponse;
import com.clubber.ClubberServer.global.vo.image.ImageVO;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecruitService {

    private final AdminReadService adminReadService;
    private final ClubRepository clubRepository;
    private final RecruitRepository recruitRepository;
    private final RecruitImageRepository recruitImageRepository;
    private final RecruitMapper recruitMapper;

    @Transactional(readOnly = true)
    public PageResponse<GetOneRecruitInListResponse> getAllAdminRecruits(Pageable pageable) {
        Admin admin = adminReadService.getAdmin();
        Club club = admin.getClub();

        Page<Recruit> recruits = recruitRepository.queryRecruitsByClub(club,
            pageable);

        return recruitMapper.getRecruitsPageResponse(recruits);
    }

    @Transactional
    public PostRecruitResponse postRecruitsPage(PostRecruitRequest requestDTO) {
        Admin admin = adminReadService.getAdmin();
        Club club = admin.getClub();

        Recruit newRecruit = Recruit.of(club, requestDTO);
        recruitRepository.save(newRecruit);

        AtomicLong order = new AtomicLong(1L);

        List<RecruitImage> savedImages = requestDTO.getImageKey().stream()
            .map(imageUrl -> {
                RecruitImage recruitImage = recruitImageRepository.save(
                    RecruitImage.of(ImageVO.valueOf(imageUrl), newRecruit)
                );
                recruitImage.updateOrderNum(order.getAndIncrement());
                return recruitImage;
            })
            .collect(Collectors.toList());

        return recruitMapper.getRecruitWithImageUrls(newRecruit, savedImages);
    }

    @Transactional
    public DeleteRecruitByIdResponse deleteRecruitsById(Long recruitId) {
        Admin admin = adminReadService.getAdmin();

        Recruit recruit = recruitRepository.queryRecruitsById(recruitId)
            .orElseThrow(() -> RecruitNotFoundException.EXCEPTION);

        if (recruit.getClub() != admin.getClub()) {
            throw RecruitDeleteUnauthorized.EXCEPTION;
        }

        List<ImageVO> imageUrls = recruitMapper.getDeletedRecruitImages(recruit);

        recruit.getRecruitImages().stream()
            .filter(recruitImage -> !recruitImage.isDeleted())
            .forEach(RecruitImage::updateStatus);
        recruit.delete();

        return DeleteRecruitByIdResponse.from(recruit, imageUrls);
    }

    @Transactional(readOnly = true)
    public PageResponse<GetOneRecruitInListResponse> getRecruitsByClubId(Long clubId,
        Pageable pageable) {
        Club club = clubRepository.findById(clubId)
            .orElseThrow(() -> ClubIdNotFoundException.EXCEPTION);

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
        Recruit recruit = recruitRepository.queryRecruitsById(recruitId)
            .orElseThrow(() -> RecruitNotFoundException.EXCEPTION);

        recruit.increaseTotalview();

        return recruitMapper.getRecruitsByRecruitId(recruit);
    }

    @Transactional(readOnly = true)
    public GetOneRecruitResponse getOneAdminRecruitsById(Long recruitId) {
        Admin admin = adminReadService.getAdmin();

        Recruit recruit = recruitRepository.queryRecruitsById(recruitId)
            .orElseThrow(() -> RecruitNotFoundException.EXCEPTION);

        if (recruit.getClub() != admin.getClub()) {
            throw RecruitUnauthorized.EXCEPTION;
        }

        return recruitMapper.getOneAdminRecruitsById(recruit);
    }

    @Transactional
    public UpdateRecruitResponse changeAdminRecruits(Long recruitId,
        UpdateRecruitRequest requestPage) {
        Admin admin = adminReadService.getAdmin();

        Recruit recruit = recruitRepository.queryRecruitsById(recruitId)
            .orElseThrow(() -> RecruitNotFoundException.EXCEPTION);

        if (recruit.getClub() != admin.getClub()) {
            throw RecruitUnauthorized.EXCEPTION;
        }

        recruit.updateRecruitPage(requestPage.getTitle(), requestPage.getContent());

        // 해당 게시글의 모든 이미지 조회
        List<RecruitImage> recruitImages = recruit.getRecruitImages()
            .stream()
            .filter(recruitImage -> !recruitImage.isDeleted())
            .collect(Collectors.toList());

        // 삭제 처리
        recruitImages.stream()
            .filter(recruitImage -> requestPage.getDeletedImageUrls().stream()
                .anyMatch(deleteImage -> deleteImage.substring(IMAGE_SERVER.length())
                    .equals(recruitImage.getImageUrl().getImageUrl())))
            .forEach(RecruitImage::updateStatus);

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
        for (String image : finalImages) {
            if (image.startsWith(IMAGE_SERVER)) {
                revisedRecruitImages.stream()
                    .filter(recruitImage -> recruitImage.getImageUrl().getImageUrl()
                        .equals(image.substring(IMAGE_SERVER.length())))
                    .forEach(recruitImage -> recruitImage.updateOrderNum(order.getAndIncrement()));
            } else {
                newImages.stream()
                    .filter(recruitImage -> recruitImage.getImageUrl().getImageUrl().equals(image))
                    .forEach(recruitImage -> recruitImage.updateOrderNum(order.getAndIncrement()));
            }
        }
        return UpdateRecruitResponse.of(recruit, requestPage.getImages());
    }
}