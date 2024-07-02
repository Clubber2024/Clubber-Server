package com.clubber.ClubberServer.domain.image.controller;

import com.clubber.ClubberServer.domain.image.dto.CreateImagePresignedUrlResponse;
import com.clubber.ClubberServer.domain.image.service.S3UploadPresignedService;
import com.clubber.ClubberServer.global.infrastructure.s3.ImageFileExtension;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class ImageController {
    private final S3UploadPresignedService s3UploadPresignedService;

    @PostMapping("/clubs/{clubId}/images")
    public CreateImagePresignedUrlResponse createClubsImagePresignedUrl(Long clubId, ImageFileExtension imageFileExtension){
        return s3UploadPresignedService.createClubsImagePresignedUrl(clubId, imageFileExtension);
    }
}
