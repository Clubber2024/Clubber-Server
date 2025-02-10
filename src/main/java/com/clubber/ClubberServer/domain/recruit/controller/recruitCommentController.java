package com.clubber.ClubberServer.domain.recruit.controller;

import com.clubber.ClubberServer.domain.recruit.dto.recruitComment.DeleteRecruitCommentResponse;
import com.clubber.ClubberServer.domain.recruit.dto.recruitComment.GetRecruitCommentResponse;
import com.clubber.ClubberServer.domain.recruit.dto.recruitComment.PostRecruitCommentRequest;
import com.clubber.ClubberServer.domain.recruit.dto.recruitComment.PostRecruitCommentResponse;
import com.clubber.ClubberServer.domain.recruit.service.RecruitCommentService;
import com.clubber.ClubberServer.global.config.swagger.DisableSwaggerSecurity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "[모집글 댓글]")
public class recruitCommentController {

    private final RecruitCommentService recruitCommentService;

    @PostMapping("/recruits/{recruitId}/comments")
    @Operation(summary = "모집글 댓글 작성")
    public PostRecruitCommentResponse postRecruitsComment(@PathVariable Long recruitId,
        @RequestBody @Valid PostRecruitCommentRequest request) {
        return recruitCommentService.postRecruitComment(recruitId, request);
    }

    @GetMapping("/recruits/{recruitId}/comments")
    @DisableSwaggerSecurity
    @Operation(summary = "모집글 댓글 조회")
    public List<GetRecruitCommentResponse> getRecruitsComments(
        @PathVariable("recruitId") Long recruitId) {
        return recruitCommentService.getRecruitComment(recruitId);
    }


    @DeleteMapping("/recruits/{recruitId}/comments/{commentId}")
    @Operation(summary = "모집글 댓글 삭제")
    public DeleteRecruitCommentResponse deleteRecruitsComment(@PathVariable Long recruitId,
        @PathVariable Long commentId) {
        return recruitCommentService.deleteRecruitComment(recruitId,commentId);
    }
}
