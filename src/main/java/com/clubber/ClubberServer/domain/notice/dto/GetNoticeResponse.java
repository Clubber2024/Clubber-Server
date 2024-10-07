package com.clubber.ClubberServer.domain.notice.dto;

import com.clubber.ClubberServer.domain.notice.domain.Notice;
import com.clubber.ClubberServer.global.vo.ImageVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetNoticeResponse {

    private Long noticeId;
    private String title;
    private String content;
    private Long totalView;
    private ImageVO imageUrl;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;

    public static GetNoticeResponse from(Notice notice){
        return GetNoticeResponse.builder()
                .noticeId(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .totalView(notice.getTotalView())
                .createdAt(notice.getCreatedAt())
                .imageUrl(notice.getImageUrl())
                .build();
    }
}
