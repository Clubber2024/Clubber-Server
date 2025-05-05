package com.clubber.ClubberServer.domain.recruit.implement;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitComment;
import com.clubber.ClubberServer.domain.recruit.dto.recruitComment.PostRecruitCommentRequest;
import com.clubber.ClubberServer.domain.recruit.exception.RecruitCommentNotFoundException;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitCommentRepository;
import com.clubber.ClubberServer.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecruitCommentAppender {

    private final RecruitCommentRepository recruitCommentRepository;

    private final RecruitCommentReader recruitCommentReader;

    public RecruitComment append(PostRecruitCommentRequest request, Recruit recruit, User user) {
        RecruitComment parentComment = recruitCommentReader.findParentComment(request.getParentId());
        RecruitComment newComment = RecruitComment.of(recruit, user, request.getContent(), parentComment);
        return recruitCommentRepository.save(newComment);
    }
}
