package com.clubber.ClubberServer.domain.notice.dto;

import com.clubber.ClubberServer.domain.notice.domain.Notice;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetNoticeResponse {
    private Long noticeId;
    private String content;
    private LocalDateTime createdAt;

    public static GetNoticeResponse from(Notice notice){
        return GetNoticeResponse.builder()
                .noticeId(notice.getId())
                .content(notice.getContent())
                .createdAt(notice.getCreatedAt())
                .build();
    }
}
