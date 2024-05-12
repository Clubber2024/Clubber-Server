package com.clubber.ClubberServer.domain.club.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class PopularClubDto {
    private Long clubId;
    private String clubName;
    private Long totalView;

    @Builder
    public PopularClubDto(Long clubId,String clubName,Long totalView) {
        this.clubId = clubId;
        this.clubName = clubName;
        this.totalView = totalView;
    }
}
