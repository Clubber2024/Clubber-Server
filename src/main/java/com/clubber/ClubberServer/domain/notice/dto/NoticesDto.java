package com.clubber.ClubberServer.domain.notice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoticesDto {
    private Long noticeId;
    private String content;
    private LocalDateTime createdAt;


    @Builder
    public NoticesDto(Long noticeId, String content,LocalDateTime createdAt){
        this.noticeId=noticeId;
        this.content=content;
        this.createdAt=createdAt;

    }
}
