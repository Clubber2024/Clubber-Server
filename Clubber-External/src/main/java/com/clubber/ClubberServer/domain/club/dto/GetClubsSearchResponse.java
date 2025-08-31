package com.clubber.ClubberServer.domain.club.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.List;
import java.util.Map;


@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubsSearchResponse {

    @Schema(description = "검색 결과 동아리 목록")
    private final Map<String, List<GetClubSearchResponse>> clubs;

    public static GetClubsSearchResponse of (Map<String, List<GetClubSearchResponse>> groupedClubs){
        return GetClubsSearchResponse.builder()
                .clubs(groupedClubs)
                .build();
    }
}