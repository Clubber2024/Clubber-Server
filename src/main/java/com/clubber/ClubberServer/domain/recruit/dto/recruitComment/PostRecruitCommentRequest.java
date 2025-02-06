package com.clubber.ClubberServer.domain.recruit.dto.recruitComment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRecruitCommentRequest {

    @NotBlank
    @Size(max = 100, message = "댓글 작성은 최대 100자입니다.")
    @Schema(description = "댓글 내용", example = "저는 추천합니다.")
    private String content;

    @Schema(description = "부모 댓글 id", example = "1")
    private Long parentId;

}
