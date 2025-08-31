package com.clubber.ClubberServer.domain.image.controller;

import com.clubber.ClubberServer.domain.image.dto.CreateImagePresignedUrlResponse;
import com.clubber.ClubberServer.domain.image.dto.CreateRecruitsImagePresigneUrlRequest;
import com.clubber.ClubberServer.domain.image.service.S3UploadPresignedService;
import com.clubber.ClubberServer.global.config.s3.ImageFileExtension;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
@Tag(name = "[이미지 등록 URL 생성]")
public class ImageController {
    private final S3UploadPresignedService s3UploadPresignedService;

    @Operation(summary = "동아리 로고 이미지 등록 Presigned URL 생성")
    @PostMapping("/club/logo")
    public CreateImagePresignedUrlResponse createClubsLogoImagePresignedUrl(@RequestParam ImageFileExtension imageFileExtension){
        return s3UploadPresignedService.createClubsLogoImagePresignedUrl(imageFileExtension);
    }

    @Operation(summary = "동아리 모집글 이미지 등록 Presigned URL 생성")
    @PostMapping("/club/recruit")
    public List<CreateImagePresignedUrlResponse> createRecruitsImagePresignedUrl(@RequestBody CreateRecruitsImagePresigneUrlRequest request) {
        return s3UploadPresignedService.createRecruitsImagePresignedUrl(request);
    }

    @Operation(summary = "동아리 관리자 회원가입시 증빙 이미지 등록 Presigned URL 생성")
    @PostMapping("/admin/sign-up/verify")
    public CreateImagePresignedUrlResponse createAdminSignUpVerifyImagePresignedUrl(@RequestParam String username, @RequestParam ImageFileExtension imageFileExtension){
        return s3UploadPresignedService.createAdminSignupVerifyImagePresignedUrl(username, imageFileExtension);
    }
}
