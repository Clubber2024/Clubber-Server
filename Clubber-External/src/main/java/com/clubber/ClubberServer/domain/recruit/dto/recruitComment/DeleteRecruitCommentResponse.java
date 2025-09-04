package com.clubber.ClubberServer.domain.recruit.dto.recruitComment;

import com.clubber.ClubberServer.domain.recruit.domain.RecruitComment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteRecruitCommentResponse {

    @Schema(description = "삭제된 댓글 id", example = "1")
    private final Long commentId;

    @Schema(description = "삭제된 댓글 내용", example = "저는 추천합니다.")
    private final String content;

    public static DeleteRecruitCommentResponse from(RecruitComment recruitComment) {
        return DeleteRecruitCommentResponse.builder()
            .commentId(recruitComment.getId())
            .content(recruitComment.getContent())
            .build();
    }
}
