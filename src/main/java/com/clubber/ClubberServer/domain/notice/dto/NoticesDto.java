package com.clubber.ClubberServer.domain.notice.dto;

import com.clubber.ClubberServer.domain.club.dto.SimpleCenterDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

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
