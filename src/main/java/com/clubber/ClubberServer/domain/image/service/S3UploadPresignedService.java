package com.clubber.ClubberServer.domain.image.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.exception.AdminNotFoundException;
import com.clubber.ClubberServer.domain.admin.repository.AdminRepository;
import com.clubber.ClubberServer.domain.club.exception.ClubNotFoundException;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.domain.image.dto.CreateImagePresignedUrlResponse;
import com.clubber.ClubberServer.domain.image.dto.CreateRecruitsImagePresigneUrlRequest;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitRepository;
import com.clubber.ClubberServer.global.config.security.SecurityUtils;
import com.clubber.ClubberServer.global.infrastructure.s3.ImageFileExtension;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class S3UploadPresignedService {

    private final AmazonS3 amazonS3;

    @Value("${aws.s3.base-url}")
    private String baseUrl;

    @Value("${aws.s3.bucket}")
    private String bucket;

    private final AdminRepository adminRepository;

    private final ClubRepository clubRepository;

    private final RecruitRepository recruitRepository;

    public CreateImagePresignedUrlResponse createClubsImagePresignedUrl(ImageFileExtension fileExtension) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Admin admin = adminRepository.findById(currentUserId)
                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);

        Long clubId = admin.getClub().getId();
        if(!clubRepository.existsClubByIdAndIsDeleted(clubId, false))
            throw ClubNotFoundException.EXCEPTION;

        String fixedFileExtension = fileExtension.getUploadExtension();
        String fileName = getForClubFileName(clubId, fixedFileExtension);
        URL url = amazonS3.generatePresignedUrl(
                getGeneratePresignedUrlRequest(bucket, fileName, fixedFileExtension));
        return CreateImagePresignedUrlResponse.of(url.toString(), fileName, baseUrl);
    }

    @Transactional(readOnly = true)
    public List<CreateImagePresignedUrlResponse> createRecruitsImagePresignedUrl(CreateRecruitsImagePresigneUrlRequest request) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Admin admin = adminRepository.findById(currentUserId)
                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);

        Long clubId = admin.getClub().getId();
        if(!clubRepository.existsClubByIdAndIsDeleted(clubId, false))
            throw ClubNotFoundException.EXCEPTION;

        Long maxRecruitId = recruitRepository.findMaxRecruitId();
        return request.getRecruitImageExtensions().stream().map(
                fileExtension -> {
                    String fixedFileExtension = fileExtension.getUploadExtension();
                    String fileName = getForRecruitFileName(clubId, maxRecruitId + 1, fixedFileExtension);
                    URL url = amazonS3.generatePresignedUrl(
                            getGeneratePresignedUrlRequest(bucket, fileName, fixedFileExtension));
                    return CreateImagePresignedUrlResponse.of(url.toString(), fileName, baseUrl);
                }).collect(Collectors.toList());
    }

    private GeneratePresignedUrlRequest getGeneratePresignedUrlRequest(
            String bucket, String fileName, String fileExtension) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(
                bucket, fileName)
                .withMethod(HttpMethod.PUT)
                .withKey(fileName)
                .withContentType("image/" + fileExtension)
                .withExpiration(getPresignedUrlExpiration());
        generatePresignedUrlRequest.addRequestParameter(
                Headers.S3_CANNED_ACL, CannedAccessControlList.PublicRead.toString());
        return generatePresignedUrlRequest;
    }

    private Date getPresignedUrlExpiration(){
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 3;
        expiration.setTime(expTimeMillis);
        return expiration;
    }

    private String getForClubFileName(Long clubId, String fileExtension) {
        return "club/" +
                clubId.toString() +
                "/" +
                UUID.randomUUID() +
                "." +
                fileExtension;
    }

    private String getForRecruitFileName(Long clubId, Long recruitId, String fileExtension) {
        return "club/" +
                clubId.toString() +
                "/" +
                "recruit/" +
                recruitId.toString() +
                "/" +
                UUID.randomUUID() +
                "." +
                fileExtension;
    }
}
