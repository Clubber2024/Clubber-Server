package com.clubber.ClubberServer.domain.notice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OneNoticeDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;


    @Builder
    public OneNoticeDto(Long id, String content,LocalDateTime createdAt){
        this.id=id;
        this.content=content;
        this.createdAt=createdAt;

    }
}
