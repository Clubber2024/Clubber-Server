package com.clubber.domain.notice.dto;

import com.clubber.domain.notice.domain.Notice;
import com.clubber.domain.common.vo.ImageVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetNoticeResponse {

    @Schema(description = "공지사항 id", example = "1")
    private final Long noticeId;

    @Schema(description = "공지사항 제목", example = "버그 수정 완료")
    private final String title;

    @Schema(description = "공지사항 내용", example = "글자 깨짐 현상 해결했습니다.")
    private final String content;

    @Schema(description = "조회수", example = "32")
    private final Long totalView;

    @Schema(description = "공지사항 이미지", example = "https://image.ssuclubber.com/notice/image2")
    private final ImageVO imageUrl;

    @Schema(description = "공지사항 생성 일자", example = "2025-01-05", type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDateTime createdAt;

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
