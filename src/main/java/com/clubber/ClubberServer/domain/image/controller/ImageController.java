package com.clubber.ClubberServer.domain.image.controller;

import com.clubber.ClubberServer.domain.image.dto.CreateImagePresignedUrlResponse;
import com.clubber.ClubberServer.domain.image.service.S3UploadPresignedService;
import com.clubber.ClubberServer.global.infrastructure.s3.ImageFileExtension;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Tag(name = "[이미지 등록 URL 생성]")
public class ImageController {
    private final S3UploadPresignedService s3UploadPresignedService;

    @Operation(summary = "동아리 로고 이미지 등록 URL 생성")
    @PostMapping("/clubs/images")
    public CreateImagePresignedUrlResponse createClubsImagePresignedUrl(@RequestParam ImageFileExtension imageFileExtension){
        return s3UploadPresignedService.createClubsImagePresignedUrl(imageFileExtension);
    }
}
