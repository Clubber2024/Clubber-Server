package com.clubber.ClubberServer.domain.image.dto;


import com.clubber.ClubberServer.global.vo.image.ImageVO;

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
    private final ImageVO imageUrl;

    @Schema(description = "이미지 key : 추후 수정 API에 요청")
    private final String imageKey;

    public static CreateImagePresignedUrlResponse of(String presignedUrl, String key){
        return CreateImagePresignedUrlResponse.builder()
                .presignedUrl(presignedUrl)
                .imageUrl(ImageVO.valueOf(key))
                .imageKey(key)
                .build();
    }
}
