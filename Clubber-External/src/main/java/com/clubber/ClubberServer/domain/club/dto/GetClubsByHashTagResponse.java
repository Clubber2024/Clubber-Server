package com.clubber.ClubberServer.domain.club.dto;


import com.clubber.domain.domains.club.domain.Hashtag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubsByHashTagResponse {

    @Schema(description = "해시태그", example = "개발")
    private final String hashtag;

    @Schema(description = "해시태그 동아리 목록")
    private final List<GetClubByHashTagResponse> clubs;

    public static GetClubsByHashTagResponse of (Hashtag hashtag, List<GetClubByHashTagResponse> clubs){
        return GetClubsByHashTagResponse.builder()
                .hashtag(hashtag.getTitle())
                .clubs(clubs)
                .build();
    }
}