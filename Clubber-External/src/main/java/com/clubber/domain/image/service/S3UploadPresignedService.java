package com.clubber.domain.image.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.clubber.domain.domains.admin.domain.Admin;
import com.clubber.domain.admin.implement.AdminReader;
import com.clubber.domain.domains.club.exception.ClubNotFoundException;
import com.clubber.domain.domains.club.repository.ClubRepository;
import com.clubber.domain.image.dto.CreateImagePresignedUrlResponse;
import com.clubber.domain.image.dto.CreateRecruitsImagePresigneUrlRequest;
import com.clubber.global.config.s3.ImageFileExtension;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class S3UploadPresignedService {

	private final AmazonS3 amazonS3;
	private final AdminReader adminReader;
	private final ClubRepository clubRepository;
	@Value("${aws.s3.bucket}")
	private String bucket;
	@Value("${aws.s3.base-url}")
	private String baseUrl;

	public CreateImagePresignedUrlResponse createClubsLogoImagePresignedUrl(
		ImageFileExtension fileExtension) {
		Admin admin = adminReader.getCurrentAdmin();

		Long clubId = admin.getClub().getId();
		if (!clubRepository.existsClubByIdAndIsDeleted(clubId, false)) {
			throw ClubNotFoundException.EXCEPTION;
		}

		String fixedFileExtension = fileExtension.getUploadExtension();
		String fileName = getForClubLogoFileName(clubId, fixedFileExtension);
		log.info("fileName = " + fileName);
		URL url = amazonS3.generatePresignedUrl(
			getGeneratePresignedUrlRequest(bucket, fileName, fixedFileExtension));
		return CreateImagePresignedUrlResponse.of(url.toString(), fileName);
	}

	public List<CreateImagePresignedUrlResponse> createRecruitsImagePresignedUrl(
		CreateRecruitsImagePresigneUrlRequest request) {
		Admin admin = adminReader.getCurrentAdmin();

		Long clubId = admin.getClub().getId();
		if (!clubRepository.existsClubByIdAndIsDeleted(clubId, false)) {
			throw ClubNotFoundException.EXCEPTION;
		}

		List<ImageFileExtension> recruitImageExtensions = request.getRecruitImageExtensions();
		UUID recruitFolder = UUID.randomUUID();
		return recruitImageExtensions.stream()
			.map((fileExtension) -> createRecruitImagePresignedUrlResponse(fileExtension, clubId,
				recruitFolder))
			.collect(Collectors.toList());
	}

	public CreateImagePresignedUrlResponse createAdminSignupVerifyImagePresignedUrl(String username, ImageFileExtension imageFileExtension){
		String fixedFileExtension = imageFileExtension.getUploadExtension();
		String fileName = getForAdminSignupVerifyFileName(username, fixedFileExtension);
		URL url = amazonS3.generatePresignedUrl(
				getGeneratePresignedUrlRequest(bucket, fileName, fixedFileExtension));
		return CreateImagePresignedUrlResponse.of(url.toString(), fileName);
	}

	private CreateImagePresignedUrlResponse createRecruitImagePresignedUrlResponse(
		ImageFileExtension fileExtension, Long clubId, UUID recruitFolder) {
		String fixedFiledExtension = fileExtension.getUploadExtension();
		String fileName = getForClubRecruitFileName(clubId, recruitFolder, fixedFiledExtension);
		URL url = amazonS3.generatePresignedUrl(
			getGeneratePresignedUrlRequest(bucket, fileName, fixedFiledExtension));
		return CreateImagePresignedUrlResponse.of(url.toString(), fileName);
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

	private Date getPresignedUrlExpiration() {
		Date expiration = new Date();
		long expTimeMillis = expiration.getTime();
		expTimeMillis += 1000 * 60 * 3;
		expiration.setTime(expTimeMillis);
		return expiration;
	}

	private String getForClubLogoFileName(Long clubId, String fileExtension) {
		return baseUrl
			+ "/club/" +
			clubId.toString() +
			"/logo/" +
			UUID.randomUUID() +
			"." +
			fileExtension;
	}

	private String getForClubRecruitFileName(Long clubId, UUID recruitFolder,
		String fileExtension) {
		return baseUrl +
			"/club/" +
			clubId.toString() +
			"/recruit/" +
			recruitFolder +
			"/" +
			UUID.randomUUID() +
			"." +
			fileExtension;
	}

	private String getForAdminSignupVerifyFileName(String username, String fileExtension){
		return baseUrl +
				"/admin/pending/" +
				username+
				"/" +
				UUID.randomUUID() +
				"." +
				fileExtension;
	}
}
