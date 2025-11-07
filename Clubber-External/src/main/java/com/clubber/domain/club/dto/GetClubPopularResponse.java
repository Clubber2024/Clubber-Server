package com.clubber.domain.club.dto;

import com.clubber.domain.domains.club.domain.Club;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubPopularResponse {

    @Schema(description = "동아리 id", example = "1")
    private final Long clubId;

    @Schema(description = "동아리명", example = "클러버")
    private final String clubName;

    @Schema(description = "조회수", example = "32")
    private final Long totalView;

    public static GetClubPopularResponse from(Club club) {
        return GetClubPopularResponse.builder()
                .clubId(club.getId())
                .clubName(club.getName())
                .totalView(club.getClubInfo().getTotalView())
                .build();
    }
}
