package com.clubber.ClubberServer.domain.recruit.dto.recruitComment;

import com.clubber.ClubberServer.domain.recruit.domain.RecruitComment;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetRecruitCommentResponse {

    @Schema(description = "댓글 삭제 여부", example = "true")
    private final boolean isDeleted;

    @Schema(description = "댓글 id", example = "1")
    private final Long commentId;

    @Schema(description = "댓글 내용", example = "저번학기 했었는데 엄청 추천합니다!")
    private final String content;

    @Schema(description = "댓글 생성 일자", example = "2025-01-05", type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDateTime createdAt;

    @Schema(description = "대댓글")
    private final List<GetRecruitCommentResponse> replies = new ArrayList<>();

    public static GetRecruitCommentResponse deletedComment(RecruitComment recruitComment) {
        return GetRecruitCommentResponse.builder()
            .commentId(recruitComment.getId())
            .isDeleted(recruitComment.isDeleted())
            .build();
    }

    public static GetRecruitCommentResponse from(RecruitComment recruitComment) {
        if (recruitComment.isDeleted()) {
            return GetRecruitCommentResponse.deletedComment(recruitComment);
        }
        return GetRecruitCommentResponse.builder()
            .commentId(recruitComment.getId())
            .content(recruitComment.getContent())
            .createdAt(recruitComment.getCreatedAt())
            .build();
    }


}
