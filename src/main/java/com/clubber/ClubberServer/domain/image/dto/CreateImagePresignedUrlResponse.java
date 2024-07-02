package com.clubber.ClubberServer.domain.image.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateImagePresignedUrlResponse {

    private final String presignedUrl;
    private final String imageUrl;
    private final String key;

    public static CreateImagePresignedUrlResponse of(String presignedUrl, String key, String baseUrl){
        return CreateImagePresignedUrlResponse.builder()
                .presignedUrl(presignedUrl)
                .imageUrl(baseUrl + "/" + key)
                .key(key)
                .build();
    }
}
