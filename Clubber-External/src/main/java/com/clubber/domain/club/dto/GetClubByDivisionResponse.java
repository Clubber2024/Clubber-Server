package com.clubber.domain.club.dto;

import com.clubber.domain.domains.club.domain.Division;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubByDivisionResponse {

    @Schema(description = "분과명", example = "연대사업분과")
    private final String division;

    @Schema(description = "분과 소속 동아리 목록")
    private final List<GetClubIntoCardResponse> clubs;

    public static GetClubByDivisionResponse of (Division division, List<GetClubIntoCardResponse> clubs){
        return GetClubByDivisionResponse.builder()
                .division(division.getTitle())
                .clubs(clubs)
                .build();
    }
}
