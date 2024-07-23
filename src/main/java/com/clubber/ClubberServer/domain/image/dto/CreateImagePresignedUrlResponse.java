package com.clubber.ClubberServer.domain.image.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateImagePresignedUrlResponse {

    @Schema(description = "presignedUrl 주소 : 해당 주소로 이미지 등록")
    private final String presignedUrl;

    @Schema(description = "이미지 조회 가능 url")
    private final String imageUrl;

    @Schema(description = "이미지 파일명")
    private final String key;

    public static CreateImagePresignedUrlResponse of(String presignedUrl, String key, String baseUrl){
        return CreateImagePresignedUrlResponse.builder()
                .presignedUrl(presignedUrl)
                .imageUrl(baseUrl + "/" + key)
                .key(key)
                .build();
    }
}
